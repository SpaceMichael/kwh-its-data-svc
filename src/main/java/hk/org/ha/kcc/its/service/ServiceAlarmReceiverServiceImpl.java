package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceAlarmReceiverDto;
import hk.org.ha.kcc.its.mapper.ServiceAlarmReceiverMapper;
import hk.org.ha.kcc.its.model.ServiceAlarmReceiver;
import hk.org.ha.kcc.its.repository.ServiceAlarmReceiverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceAlarmReceiverServiceImpl implements ServiceAlarmReceiverService {

    private final ServiceAlarmReceiverRepository serviceAlarmReceiverRepository;

    private final ServiceAlarmReceiverMapper serviceAlarmReceiverMapper;

    public ServiceAlarmReceiverServiceImpl(ServiceAlarmReceiverRepository serviceAlarmReceiverRepository, ServiceAlarmReceiverMapper serviceAlarmReceiverMapper) {
        this.serviceAlarmReceiverRepository = serviceAlarmReceiverRepository;
        this.serviceAlarmReceiverMapper = serviceAlarmReceiverMapper;
    }

    @Override
    public ServiceAlarmReceiverDto createDto(ServiceAlarmReceiverDto ServiceAlarmReceiverDto) {
        ServiceAlarmReceiver serviceAlarmReceiver = serviceAlarmReceiverMapper.ServiceAlarmReceiverDtoToServiceAlarmReceiver(ServiceAlarmReceiverDto);
        if (ServiceAlarmReceiverDto.getActiveFlag() != null) {
            serviceAlarmReceiver.setActiveFlag(ServiceAlarmReceiverDto.getActiveFlag());
        }
        serviceAlarmReceiverRepository.save(serviceAlarmReceiver);
        return serviceAlarmReceiverMapper.ServiceAlarmReceiverToServiceAlarmReceiverDto(serviceAlarmReceiver);
    }

    @Override
    public List<ServiceAlarmReceiverDto> getAllDto() {
        List<ServiceAlarmReceiver> serviceAlarmReceiverList = serviceAlarmReceiverRepository.findAll().stream().filter(ServiceAlarmReceiver::getActiveFlag).collect(Collectors.toList());
        return serviceAlarmReceiverList.stream().map(serviceAlarmReceiverMapper::ServiceAlarmReceiverToServiceAlarmReceiverDto).collect(Collectors.toList());
    }

    @Override
    public ServiceAlarmReceiverDto getDtoById(int id) {
        ServiceAlarmReceiver serviceAlarmReceiver = serviceAlarmReceiverRepository.findById(id).stream().filter(ServiceAlarmReceiver::getActiveFlag).findFirst().orElseThrow(() -> new ResourceNotFoundException("ServiceAlarmReceiver not found"));
        return serviceAlarmReceiverMapper.ServiceAlarmReceiverToServiceAlarmReceiverDto(serviceAlarmReceiver);
    }

    @Override
    public ServiceAlarmReceiverDto updateDtoById(int id, ServiceAlarmReceiverDto serviceAlarmReceiverDto) {
        ServiceAlarmReceiver serviceAlarmReceiver = serviceAlarmReceiverRepository.findById(id).stream().filter(ServiceAlarmReceiver::getActiveFlag).findFirst().orElseThrow(() -> new ResourceNotFoundException("ServiceAlarmReceiver not found"));
        if (serviceAlarmReceiverDto.getServiceCode() != null) {
            serviceAlarmReceiver.setServiceCode(serviceAlarmReceiverDto.getServiceCode());
        }
        if (serviceAlarmReceiverDto.getStartTime() != null) {
            serviceAlarmReceiver.setStartTime(serviceAlarmReceiverDto.getStartTime());
        }
        if (serviceAlarmReceiverDto.getEndTime() != null) {
            serviceAlarmReceiver.setEndTime(serviceAlarmReceiverDto.getEndTime());
        }
        if (serviceAlarmReceiverDto.getAckTimeout() != null) {
            serviceAlarmReceiver.setAckTimeout(serviceAlarmReceiverDto.getAckTimeout());
        }
        if (serviceAlarmReceiverDto.getEscalationId() != null) {
            serviceAlarmReceiver.setEscalationId(serviceAlarmReceiverDto.getEscalationId());
        }
        if (serviceAlarmReceiverDto.getActiveFlag() != null) {
            serviceAlarmReceiver.setActiveFlag(serviceAlarmReceiverDto.getActiveFlag());
        }
        //save and return
        return serviceAlarmReceiverMapper.ServiceAlarmReceiverToServiceAlarmReceiverDto(serviceAlarmReceiverRepository.save(serviceAlarmReceiver));
    }

    @Override
    public void delDtoById(int id) {
        try {
            this.serviceAlarmReceiverRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("ServiceAlarmReceiver not found");
        }
    }
}
