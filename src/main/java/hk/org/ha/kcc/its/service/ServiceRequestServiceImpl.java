package hk.org.ha.kcc.its.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
        // get sender id to override ServiceAlarmSender
        alarmDto.setSenderGroupIds(serviceAlarmSenderList.stream().findFirst().get().getSenderId().toString());
        log.debug("sender id: " + alarmDto.getSenderGroupIds());
        // find serviceAlarmReceiver
        List<ServiceAlarmReceiver> serviceAlarmReceiverlist = sServiceAlarmReceiverRepository.findAll().stream()
                .filter(s -> s.getServiceCode().equals(serviceCode))
                .filter(serviceAlarmReceiver -> {
                    LocalDateTime endTime = serviceAlarmReceiver.getEndTime().atDate(LocalDate.now());
                    LocalDateTime endTime2 = null;
                    LocalDateTime startTime = serviceAlarmReceiver.getStartTime().atDate(LocalDate.now());
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
            throw new ResourceNotFoundException("Alarm call false please check!");
        } else {
            serviceRequest1.setSuccess(true);
        }
        // update service request
        serviceRequest1.setAlarmId(atWorkAlarmResponseDto.getData().getId());
        if (alarmDto.getEscalationId() != null) {
            serviceRequest1.setEscalationId(alarmDto.getEscalationId());
        }
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
}
