package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.dto.AlarmResponseDto;
import hk.org.ha.kcc.its.dto.ServiceRequestDto;
import hk.org.ha.kcc.its.mapper.ServiceRequestMapper;
import hk.org.ha.kcc.its.model.ServiceRequest;
import hk.org.ha.kcc.its.repository.ServiceRepository;
import hk.org.ha.kcc.its.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    private final AlarmService alarmService;

    private final ServiceRepository serviceRepository;

    public ServiceRequestServiceImpl(ServiceRequestRepository serviceRequestRepository, ServiceRequestMapper serviceRequestMapper, AlarmService alarmService, ServiceRepository serviceRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestMapper = serviceRequestMapper;
        this.alarmService = alarmService;
        this.serviceRepository = serviceRepository;
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
        // get AlarmDto by SR id and location
        AlarmDto alarmDto = alarmService.getDtoBySRId(serviceRequestDto.getServiceId(), serviceRequestDto.getLocation());
        AlarmResponseDto alarmResponseDto = alarmService.create(alarmDto);
        // update the alarm id
        serviceRequest1.setAlarmId(alarmResponseDto.getId());
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
