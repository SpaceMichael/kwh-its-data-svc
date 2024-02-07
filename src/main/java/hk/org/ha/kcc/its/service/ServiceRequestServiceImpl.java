package hk.org.ha.kcc.its.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;

import hk.org.ha.kcc.its.dto.ServiceRequestDto;
import hk.org.ha.kcc.its.dto.alarm.AlarmDto;
import hk.org.ha.kcc.its.dto.alarm.AtWorkAlarmResponseDto;

import hk.org.ha.kcc.its.mapper.ServiceRequestMapper;
import hk.org.ha.kcc.its.model.ServiceAlarmSender;
import hk.org.ha.kcc.its.model.ServiceAlarmReceiver;
import hk.org.ha.kcc.its.model.ServiceRequest;
import hk.org.ha.kcc.its.model.Services;
import hk.org.ha.kcc.its.repository.ServiceAlarmSenderRepository;
import hk.org.ha.kcc.its.repository.ServiceAlarmReceiverRepository;
import hk.org.ha.kcc.its.repository.ServiceRepository;
import hk.org.ha.kcc.its.repository.ServiceRequestRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;

@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceAlarmSenderRepository serviceAlarmSenderRepository;

    private final ServiceAlarmReceiverRepository sServiceAlarmReceiverRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    private final ServiceRepository serviceRepository;

    private final AlarmService alarmService;

    @Value("${app.calendar.api.path}")
    private String calendarApiPath;

    @Value("${app.auth.api.path}")
    private String authApiPath;

    @Value("${app.auth.api.password}")
    private String password;

    @Value("${app.auth.api.user}")
    private String user;

    public ServiceRequestServiceImpl(ServiceRequestRepository serviceRequestRepository, ServiceAlarmSenderRepository serviceAlarmSenderRepository, ServiceAlarmReceiverRepository sServiceAlarmReceiverRepository, ServiceRequestMapper serviceRequestMapper, ServiceRepository serviceRepository, AlarmService alarmService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceAlarmSenderRepository = serviceAlarmSenderRepository;
        this.sServiceAlarmReceiverRepository = sServiceAlarmReceiverRepository;
        this.serviceRequestMapper = serviceRequestMapper;
        this.serviceRepository = serviceRepository;
        this.alarmService = alarmService;
    }

    @Override
    public List<ServiceRequestDto> getAllDto() {
        // filter get active flag is true and then mapper to dto
        /*String hostname = serviceRequestRepository.gethostname();
        log.debug("hostname: " + hostname);*/
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        return serviceRequestList.stream().map(serviceRequestMapper::ServiceRequestToServiceRequestDto).collect(Collectors.toList());
    }

    @Override
    public ServiceRequestDto getDtoById(String id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
        return serviceRequestMapper.ServiceRequestToServiceRequestDto(serviceRequest);
    }

    @Override
    public ServiceRequestDto create(ServiceRequestDto serviceRequestDto) {
        /*String hostname = serviceRequestRepository.gethostname();
        log.debug("hostname: " + hostname);*/
        // get day of week
        LocalDate date = LocalDate.now();
        //LocalDate date = LocalDate.of(2024, 2, 4); for quick test
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // date formart change to YYYY-MM-DD
        String dateStr = date.toString();
        // test 2024-02-04
        //String dateStr ="2024-02-04";
        //KCC calendar path
        String path = calendarApiPath + dateStr + "&endDate=" + dateStr;
        // get JWT token
        String jwtToken = getJwtToken();
        // use webclient to get public holiday for check
        WebClient webClient = WebClient.create();

        // send api request with two parameters startDate and endDate
        ResponseEntity<String> response = webClient.get()
                .uri(path)
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .toEntity(String.class)
                .block();
        //check PH is ture if response type is P ,
        boolean PH = false;
        if (!Objects.equals(response != null ? response.getBody() : null, "[]")) {
            try {
                String responseBody = response != null ? response.getBody() : null;
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                PH = jsonNode.get(0).get("type").asText().equals("P");
            } catch (JsonProcessingException e) {
                log.debug(e.toString());
            }
        }
        /* java vs HA
        DayOfWeek.MONDAY corresponds to 1 HA 2
        DayOfWeek.TUESDAY corresponds to 2 HA 3
        DayOfWeek.WEDNESDAY corresponds to 3 HA 4
        DayOfWeek.THURSDAY corresponds to 4 HA 5
        DayOfWeek.FRIDAY corresponds to 5 HA 6
        DayOfWeek.SATURDAY corresponds to 6 HA 7
        DayOfWeek.SUNDAY corresponds to 7 HA 1
         */
        ServiceRequest serviceRequest = serviceRequestMapper.ServiceRequestDtoToServiceRequest(serviceRequestDto);
        // save
        ServiceRequest serviceRequest1 = serviceRequestRepository.save(serviceRequest);
        // set alarm
        AlarmDto alarmDto = new AlarmDto();
        // get service code by service id
        Services services = serviceRepository.findById(serviceRequest1.getServiceId()).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        String serviceCode = services.getServiceCode();
        // localDateTime is now
        LocalDateTime creatTime = LocalDateTime.now();
        // get ServiceAlarmSender by services.service_code and location
        List<ServiceAlarmSender> serviceAlarmSenderList = serviceAlarmSenderRepository.findByServiceCodeLike(serviceCode, serviceRequest1.getLocation());
        // CHECK null
        if (serviceAlarmSenderList.isEmpty()) {
            log.debug("serviceAckReceiverList is empty: " + serviceCode + " and  " + serviceRequest1.getLocation());
            throw new ResourceNotFoundException("please check serviceCode and location not found: " + serviceCode + " and  " + serviceRequest1.getLocation());
        }
        String senderId = serviceAlarmSenderList.stream().findFirst().get().getSenderId();
        // get sender id to override ServiceAlarmSender suppose only one sender Id
        if (senderId != null) {
            //log.debug("sender id: " + senderId);
            alarmDto.setSenderGroupIds(senderId);
            serviceRequest1.setSenderId(senderId);
        } else {
            log.debug("sender id is null: " + alarmDto.getSenderGroupIds());
        }
        // find serviceAlarmReceiver
        boolean finalPH = PH;
        List<ServiceAlarmReceiver> serviceAlarmReceiverlist = sServiceAlarmReceiverRepository.findAll().stream()
                .filter(s -> s.getServiceCode().equals(serviceCode))
                // start time is not null and end time is not null
                .filter(serviceAlarmReceiver -> serviceAlarmReceiver.getStartTime() != null && serviceAlarmReceiver.getEndTime() != null)
                .filter(serviceAlarmReceiver -> {
                    // if dayOfWeek =1 , the start time is start_time_sun , end time is end_time_sun  , if dayOfWeek = 7 , the start time is start_time_sat , end time is end_time_sat, others is start_time and end_time
                    LocalDateTime startTime;
                    LocalDateTime endTime;
                    // if  dayOfWeek.getValue() == 7 or PH is true , the start time is start_time_sun , end time is end_time_sun
                    if (finalPH && serviceAlarmReceiver.getStartTimeSun() != null && serviceAlarmReceiver.getEndTimeSun() != null) {
                        startTime = serviceAlarmReceiver.getStartTimeSun().atDate(LocalDate.now());
                        endTime = serviceAlarmReceiver.getEndTimeSun().atDate(LocalDate.now());
                    } else if (dayOfWeek.getValue() == 7 && serviceAlarmReceiver.getStartTimeSun() != null && serviceAlarmReceiver.getEndTimeSun() != null) { // sunday
                        startTime = serviceAlarmReceiver.getStartTimeSun().atDate(LocalDate.now());
                        endTime = serviceAlarmReceiver.getEndTimeSun().atDate(LocalDate.now());
                    } else if (dayOfWeek.getValue() == 6 && serviceAlarmReceiver.getStartTimeSat() != null && serviceAlarmReceiver.getEndTimeSat() != null) { // saturday
                        startTime = serviceAlarmReceiver.getStartTimeSat().atDate(LocalDate.now());
                        endTime = serviceAlarmReceiver.getEndTimeSat().atDate(LocalDate.now());
                    } else {
                        startTime = serviceAlarmReceiver.getStartTime().atDate(LocalDate.now());
                        endTime = serviceAlarmReceiver.getEndTime().atDate(LocalDate.now());
                    }
                    //log.debug("startTime: " + startTime + " endTime: " + endTime);
                    LocalDateTime endTime2 = null;
                    LocalDateTime startTime2 = null;
                    if (startTime.isAfter(endTime)) {
                        startTime2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
                        endTime2 = endTime;
                        endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
                    }
                    // add if startTime2 and endTime2 is not null
                    if (startTime2 != null) {
                        return creatTime.isAfter(startTime) && creatTime.isBefore(endTime) || creatTime.isAfter(startTime2) && creatTime.isBefore(endTime2);
                    } else {
                        return creatTime.isAfter(startTime) && creatTime.isBefore(endTime);
                    }
                })
                .toList();
        // check null
        if (serviceAlarmReceiverlist.isEmpty()) {
            log.debug("serviceAlarmReceiverlist. please check serviceCode, and start and end time" + serviceCode);
            throw new ResourceNotFoundException("please check serviceCode, and start and end time." + serviceCode);
        }

        // set alarmDto
        alarmDto.setAlarmType(services.getAlarmType());
        alarmDto.setEscalationId(serviceAlarmReceiverlist.stream().findFirst().get().getEscalationId());
        if (serviceAlarmReceiverlist.stream().findFirst().get().getAlarmTitle() != null || !serviceAlarmReceiverlist.stream().findFirst().get().getAlarmTitle().isEmpty()) {
            alarmDto.setTitle(MessageFormat.format(serviceAlarmReceiverlist.stream().findFirst().get().getAlarmTitle(), serviceRequest1.getLocation()));
        }
        if (serviceAlarmReceiverlist.stream().findFirst().get().getAlarmMessage() != null || !serviceAlarmReceiverlist.stream().findFirst().get().getAlarmMessage().isEmpty()) {
            alarmDto.setMessage(MessageFormat.format(serviceAlarmReceiverlist.stream().findFirst().get().getAlarmMessage(), serviceRequest1.getCaseNo(), serviceRequest1.getLocation(), serviceRequest1.getBedNo(), serviceRequest1.getRemarks()));
        }

        // get resp from alarm
        AtWorkAlarmResponseDto atWorkAlarmResponseDto = alarmService.create(alarmDto);
        if (!atWorkAlarmResponseDto.getSuccess()) {
            serviceRequest1.setErrorMessage(atWorkAlarmResponseDto.getError().getMessage());
        }
        // check resp when false
        if (!atWorkAlarmResponseDto.getSuccess()) {
            serviceRequest1.setSuccess(false);
            serviceRequest1.setErrorMessage(atWorkAlarmResponseDto.getError().getMessage());
            throw new ResourceNotFoundException("Alarm call false please check!");
        } else {
            serviceRequest1.setSuccess(true);
        }
        //  check alarmId and escalationId is null
        if (atWorkAlarmResponseDto.getData().getId() != null) {
            serviceRequest1.setAlarmId(atWorkAlarmResponseDto.getData().getId());
        }
        if (alarmDto.getEscalationId() != null) {
            serviceRequest1.setEscalationId(alarmDto.getEscalationId());
        }
        /*
        Optional.ofNullable(atWorkAlarmResponseDto.getData().getId()).ifPresent(serviceRequest1::setAlarmId);
        Optional.ofNullable(alarmDto.getEscalationId()).ifPresent(serviceRequest1::setEscalationId);
         */
        return serviceRequestMapper.ServiceRequestToServiceRequestDto(serviceRequest1);
    }

    @Override
    public ServiceRequestDto updateById(String id, ServiceRequestDto serviceRequestDto) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
        BeanUtils.copyProperties(serviceRequestDto, serviceRequest, getNullPropertyNames(serviceRequestDto));
        return serviceRequestMapper.ServiceRequestToServiceRequestDto(serviceRequestRepository.save(serviceRequest));
    }

    @Override
    public void deleteById(String id) {
        serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
        serviceRequestRepository.deleteById(id);
    }

    private String getJwtToken() {
        String jwtToken;
        String jwtTokenUrl = authApiPath;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("username", user);
            requestBody.put("password", password);
            // use webclient call
            WebClient webClient = WebClient.create();
            ResponseEntity<String> response = webClient.post()
                    .uri(jwtTokenUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            assert response != null;
            String responseBody = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            jwtToken = jsonNode.get("token").asText();
        } catch (Exception e) {
            throw new ResourceNotFoundException("JWT Token not found");
        }
        return jwtToken;
    }
}
