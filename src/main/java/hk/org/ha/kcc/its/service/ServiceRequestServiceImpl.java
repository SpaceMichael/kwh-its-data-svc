package hk.org.ha.kcc.its.service;

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

        // get day of week
        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println("dayOfWeek: " + dayOfWeek + " dayOfWeek.getValue: " + dayOfWeek.getValue());
        // date formart change to YYYY-MM-DD
        String dateStr = date.toString();
        //System.out.println("dateStr: " + dateStr);

        //KCC calendar path
        String path = "https://kcc-calendar-svc-kccclinical-stag-prd.prdcld61.server.ha.org.hk/api/v1/calendar/public-holiday?startDate=2024-02-10&endDate=" + dateStr;
        log.debug("path: " + path);
        // get JWT token
        String jwtToken = getJwtToken();
        // use webclient to get public holiday for check
        WebClient webClient = WebClient.create();
        ResponseEntity<String> response = webClient.get()
                .uri(path)
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .toEntity(String.class)
                .block();
        // check response
        System.out.println("test response" + response);


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
        List<ServiceAlarmReceiver> serviceAlarmReceiverlist = sServiceAlarmReceiverRepository.findAll().stream()
                .filter(s -> s.getServiceCode().equals(serviceCode))
                .filter(serviceAlarmReceiver -> {
                    // if dayOfWeek =1 , the start time is start_time_sun , end time is end_time_sun  , if dayOfWeek = 7 , the start time is start_time_sat , end time is end_time_sat, others is start_time and end_time
                    LocalDateTime startTime;
                    LocalDateTime endTime;
                    if (dayOfWeek.getValue() == 7) { // sunday
                        startTime = serviceAlarmReceiver.getStartTimeSun().atDate(LocalDate.now());
                        endTime = serviceAlarmReceiver.getEndTimeSun().atDate(LocalDate.now());
                    } else if (dayOfWeek.getValue() == 6) { // saturday
                        startTime = serviceAlarmReceiver.getStartTimeSat().atDate(LocalDate.now());
                        endTime = serviceAlarmReceiver.getEndTimeSat().atDate(LocalDate.now());
                    } else {
                        startTime = serviceAlarmReceiver.getStartTime().atDate(LocalDate.now());
                        endTime = serviceAlarmReceiver.getEndTime().atDate(LocalDate.now());
                    }
                    LocalDateTime endTime2 = null;
                    LocalDateTime startTime2 = null;
                    if (startTime.isAfter(endTime)) {
                        startTime2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
                        endTime2 = endTime;
                        //startTime = startTime;
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
        String jwtTokenUrl = "https://kcc-auth-svc-kccclinical-stag-prd.prdcld61.server.ha.org.hk/api/v1/auth";
        //System.out.println("jwtTokenUrl: " + jwtTokenUrl);
        try {
            String url = jwtTokenUrl;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("username", "admin");
            requestBody.put("password", "ecppass");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // use webclient replace restTemplate
            WebClient webClient = WebClient.create();
            ResponseEntity<String> response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            //ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
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
