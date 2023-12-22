package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.dto.AlarmResponseDto;
import hk.org.ha.kcc.its.dto.ServiceRequestDto;
import hk.org.ha.kcc.its.mapper.ServiceRequestMapper;
import hk.org.ha.kcc.its.model.ServiceAckReceiver;
import hk.org.ha.kcc.its.model.ServiceAlarmReceiver;
import hk.org.ha.kcc.its.model.ServiceRequest;
import hk.org.ha.kcc.its.model.Services;
import hk.org.ha.kcc.its.repository.ServiceAckReceiverRepository;
import hk.org.ha.kcc.its.repository.ServiceAlarmReceiverRepository;
import hk.org.ha.kcc.its.repository.ServiceRepository;
import hk.org.ha.kcc.its.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceAckReceiverRepository serviceAckReceiverRepository;

    private final ServiceAlarmReceiverRepository sServiceAlarmReceiverRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    private final ServiceRepository serviceRepository;

    private final AlarmService alarmService;

    public ServiceRequestServiceImpl(ServiceRequestRepository serviceRequestRepository, ServiceAckReceiverRepository serviceAckReceiverRepository, ServiceAlarmReceiverRepository sServiceAlarmReceiverRepository, ServiceRequestMapper serviceRequestMapper, ServiceRepository serviceRepository, AlarmService alarmService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceAckReceiverRepository = serviceAckReceiverRepository;
        this.sServiceAlarmReceiverRepository = sServiceAlarmReceiverRepository;
        this.serviceRequestMapper = serviceRequestMapper;
        this.serviceRepository = serviceRepository;
        this.alarmService = alarmService;
    }

    @Override
    public List<ServiceRequestDto> getAllDto() {
        // filter get active flag is true and then mapper to dto
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll().stream()
                .filter(ServiceRequest::getActiveFlag)
                .collect(Collectors.toList());
        return serviceRequestList.stream().map(serviceRequestMapper::ServiceRequestToServiceRequestDto).collect(Collectors.toList());
    }

    @Override
    public ServiceRequestDto getDtoById(String id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id).filter(ServiceRequest::getActiveFlag).orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
        return serviceRequestMapper.ServiceRequestToServiceRequestDto(serviceRequest);
    }

    @Override
    public ServiceRequestDto create(ServiceRequestDto serviceRequestDto) {
        ServiceRequest serviceRequest = serviceRequestMapper.ServiceRequestDtoToServiceRequest(serviceRequestDto);
        if (serviceRequestDto.getActiveFlag() != null) {
            serviceRequest.setActiveFlag(serviceRequestDto.getActiveFlag());
        } else {
            serviceRequest.setActiveFlag(true);
        }
        // save
        ServiceRequest serviceRequest1 = serviceRequestRepository.save(serviceRequest);
        // set alarm
        AlarmDto alarmDto = new AlarmDto();
        // get service code by service id
        Services services = serviceRepository.findById(serviceRequest1.getServiceId()).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        String serviceCode = services.getServiceCode();
        LocalDateTime creatTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 0, 0)); //  test 02:00:00 test 10:00 test 20:00:00
        // get serviceAckReceiver by services.service_code and location
        List<ServiceAckReceiver> serviceAckReceiverList = serviceAckReceiverRepository.findByServiceCodeLike(serviceCode, serviceRequest1.getLocation());
        // CHECK NULL
        if (serviceAckReceiverList.isEmpty()) {
            //System.out.println("serviceAckReceiverList is empty");
            log.debug("serviceAckReceiverList is empty");
            throw new ResourceNotFoundException("ServiceAckReceiver not found");
        }
        // find serviceAlarmReceiver
        List<ServiceAlarmReceiver> serviceAlarmReceiverlist = sServiceAlarmReceiverRepository.findAll().stream()
                .filter(s -> s.getServiceCode().equals(serviceCode))
                // compare creatTime with startTime > creatTime < endTime if serviceAlarmReceiver.getEndTime().atDate(LocalDate.now()) < serviceAlarmReceiver.getStartTime().atDate(LocalDate.now()); serviceAlarmReceiver.getEndTime().atDate(LocalDate.now())+1
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
                .collect(Collectors.toList());
        // Set title template
        // String title = MessageFormat.format(serviceAlarmReceiverlist.stream().findFirst().get().getAlarmTitle(), serviceRequest1.getLocation());

        String Message0 = MessageFormat.format(serviceAlarmReceiverlist.stream().findFirst().stream().filter(s -> s.getServiceCode().equals(serviceCode)).findFirst().get().getAlarmMessage(),
                serviceRequest1.getCaseNo(), serviceRequest1.getLocation(), serviceRequest1.getBedNo(), serviceRequest1.getRemarks());

        /* String Message = MessageFormat.format("Case no: {0}\\nWard Code: {1}\\nBed No.: {2}\\nRemark: {3}",
                serviceRequest1.getCaseNo(), serviceRequest1.getLocation(),serviceRequest1.getBedNo(),serviceRequest1.getRemarks());*/

        // set alarmDto
        alarmDto.setRequestId(serviceRequest1.getId());
        alarmDto.setAckEscalationId(serviceAckReceiverList.stream().findFirst().get().getEscalationId());
        alarmDto.setToEscalationId(serviceAlarmReceiverlist.stream().findFirst().get().getEscalationId());
        alarmDto.setSeverity("normal");
        alarmDto.setType("Houseman");
        alarmDto.setTitle(MessageFormat.format(serviceAlarmReceiverlist.stream().findFirst().get().getAlarmTitle(), serviceRequest1.getLocation()));
        alarmDto.setMessage(MessageFormat.format(serviceAlarmReceiverlist.stream().findFirst().stream().filter(s -> s.getServiceCode().equals(serviceCode)).findFirst().get().getAlarmMessage(),
                serviceRequest1.getCaseNo(), serviceRequest1.getLocation(), serviceRequest1.getBedNo(), serviceRequest1.getRemarks()));
        alarmDto.setAckThreshold(1); // hardcode in db service_ack_receiver?
        alarmDto.setWebhook(true);
        alarmDto.setAckTimeout(1);
        alarmDto.setNotificationRequired(true);

        // get resp from alarm
        AlarmResponseDto alarmResponseDto = alarmService.create(alarmDto);

        if (alarmResponseDto.getSuccess().equals("false")) {
            throw new ResourceNotFoundException("Alarm call false please check!");
        }

        // update service request
        serviceRequest1.setAlarmId(alarmResponseDto.getId());


        return serviceRequestMapper.ServiceRequestToServiceRequestDto(serviceRequest1);
    }

    @Override
    public ServiceRequestDto updateById(String id, ServiceRequestDto serviceRequestDto) {

        ServiceRequest serviceRequest = serviceRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
        //update by Dto if the value is not null
        if (serviceRequestDto.getCaseNo() != null) {
            serviceRequest.setCaseNo(serviceRequestDto.getCaseNo());
        }
        if (serviceRequestDto.getLocation() != null) {
            serviceRequest.setLocation(serviceRequestDto.getLocation());
        }
        if (serviceRequestDto.getCubicleNo() != null) {
            serviceRequest.setCubicleNo(serviceRequestDto.getCubicleNo());
        }
        if (serviceRequestDto.getBedNo() != null) {
            serviceRequest.setBedNo(serviceRequestDto.getBedNo());
        }
        if (serviceRequestDto.getServiceId() != null) {
            serviceRequest.setServiceId(serviceRequestDto.getServiceId());
        }
        /*if (serviceRequestDto.getServiceName() != null) {
            serviceRequest.setServiceName(serviceRequestDto.getServiceName());
        }*/
        if (serviceRequestDto.getRemarks() != null) {
            serviceRequest.setRemarks(serviceRequestDto.getRemarks());
        }
        if (serviceRequestDto.getActiveFlag() != null) {
            serviceRequest.setActiveFlag(serviceRequestDto.getActiveFlag());
        }
        if (serviceRequestDto.getAlarmId() != null) {
            serviceRequest.setAlarmId(serviceRequestDto.getAlarmId());
        }
        // save
        return serviceRequestMapper.ServiceRequestToServiceRequestDto(serviceRequestRepository.save(serviceRequest));
    }

    @Override
    public void deleteById(String id) {
        try {
            serviceRequestRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("ServiceRequest not found");
        }
    }
}
