package hk.org.ha.kcc.its.service;

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

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

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
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
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
        String ServiceCode = services.getServiceCode();
        // get the hh:mm from serviceRequest1.getCreatedDate()
        LocalTime localTime = serviceRequest1.getCreatedDate().toLocalTime();
        // get serviceAckReceiver by services.service_code
/*        ServiceAckReceiver serviceAckReceiver = serviceAckReceiverRepository.findAll().stream()
                .filter(s -> s.getServiceCode().equals(ServiceCode))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("ServiceAckReceiver not found"));*/
        List<ServiceAlarmReceiver> serviceAlarmReceiverlist = sServiceAlarmReceiverRepository.findAll().stream()
                .filter(s -> s.getServiceCode().equals(ServiceCode))
                //compare the time
                //.filter(s -> s.getStartTime().isAfter(localTime) && s.getEndTime().isBefore(localTime))
                //.filter(s -> localTime.isAfter(s.getStartTime()))
                //.filter(s -> localTime.isBefore(s.getEndTime()))
                .collect(Collectors.toList());
        for (ServiceAlarmReceiver serviceAlarmReceiver : serviceAlarmReceiverlist) {
            // if starttime > end time, then end time +24hr
            LocalTime localTime1 =serviceAlarmReceiver.getEndTime();
            if (serviceAlarmReceiver.getStartTime().isAfter(serviceAlarmReceiver.getEndTime())) {
                localTime1= localTime1.plusHours(24);
            }
            if (serviceAlarmReceiver.getStartTime().isBefore(localTime) && localTime1.isAfter(localTime)) {
                alarmDto.setSeverity("normal");
            } else {
                alarmDto.setSeverity("critical");
            }
        }
        //.findFirst()
        //.orElseThrow(() -> new ResourceNotFoundException("ServiceAlarmReceiver not found"));
/*        LocalTime localTime1 = serviceAlarmReceiver.getStartTime();
        LocalTime localTime2 = serviceAlarmReceiver.getEndTime();
        // compare localTime and localTime1
        //if (localTime.isAfter(localTime1)) {
        if (localTime.isBefore(localTime2)) {
            alarmDto.setSeverity("normal");
        } else {
            alarmDto.setSeverity("critical");
        }*/


        System.out.println("test");


        // get resp
        AlarmResponseDto alarmResponseDto = alarmService.create(alarmDto);


        // udpate service request
        serviceRequest1.setAlarmId(alarmResponseDto.getId());



        /*// get AlarmDto by SR id and location
        AlarmDto alarmDto = alarmService.getDtoBySRId(serviceRequestDto.getServiceId(), serviceRequestDto.getLocation());
        AlarmResponseDto alarmResponseDto = alarmService.create(alarmDto);
        // update the alarm id
        serviceRequest1.setAlarmId(alarmResponseDto.getId());*/
        // return
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
