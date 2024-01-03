package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceAckReceiverDto;
import hk.org.ha.kcc.its.mapper.ServiceAckReceiverMapper;
import hk.org.ha.kcc.its.model.ServiceAckReceiver;
import hk.org.ha.kcc.its.repository.ServiceAckReceiverRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;

@Service
@Transactional
public class ServiceAckReceiverServiceImpl implements ServiceAckReceiverService {
    private final ServiceAckReceiverRepository serviceAckReceiverRepository;
    private final ServiceAckReceiverMapper serviceAckReceiverMapper;

    public ServiceAckReceiverServiceImpl(ServiceAckReceiverRepository serviceAckReceiverRepository, ServiceAckReceiverMapper serviceAckReceiverMapper) {
        this.serviceAckReceiverRepository = serviceAckReceiverRepository;
        this.serviceAckReceiverMapper = serviceAckReceiverMapper;
    }

    @Override
    public ServiceAckReceiverDto create(ServiceAckReceiverDto serviceAckReceiverDto) {
        ServiceAckReceiver serviceAckReceiver = serviceAckReceiverMapper.ServiceAckReceiverDtoToServiceAckReceiver(serviceAckReceiverDto);
        if (serviceAckReceiverDto.getActiveFlag() != null) {
            serviceAckReceiver.setActiveFlag(serviceAckReceiverDto.getActiveFlag());
        }
        return serviceAckReceiverMapper.ServiceAckReceiverToServiceAckReceiverDto(serviceAckReceiverRepository.save(serviceAckReceiver));
    }

    @Override
    public List<ServiceAckReceiverDto> getAllDto() {
        List<ServiceAckReceiver> serviceAckReceiverList = serviceAckReceiverRepository.findAll().stream().filter(ServiceAckReceiver::getActiveFlag).collect(Collectors.toList());
        return serviceAckReceiverList.stream().map(serviceAckReceiverMapper::ServiceAckReceiverToServiceAckReceiverDto).collect(Collectors.toList());
    }

    @Override
    public ServiceAckReceiverDto getDtoById(int id) {
        ServiceAckReceiver serviceAckReceiver = serviceAckReceiverRepository.findById(id).stream().filter(ServiceAckReceiver::getActiveFlag).findFirst().orElseThrow(() -> new ResourceNotFoundException("ServiceAckReceiver not found"));
        return serviceAckReceiverMapper.ServiceAckReceiverToServiceAckReceiverDto(serviceAckReceiver);
    }

    @Override
    public ServiceAckReceiverDto updateDtoById(int id, ServiceAckReceiverDto serviceAckReceiverDto) {
        ServiceAckReceiver serviceAckReceiver = serviceAckReceiverRepository.findById(id).stream().filter(ServiceAckReceiver::getActiveFlag).findFirst().orElseThrow(() -> new ResourceNotFoundException("ServiceAckReceiver not found"));
        BeanUtils.copyProperties(serviceAckReceiverDto, serviceAckReceiver, getNullPropertyNames(serviceAckReceiverDto));
        /*if (serviceAckReceiverDto.getServiceCode() != null) {
            serviceAckReceiver.setServiceCode(serviceAckReceiverDto.getServiceCode());
        }
        if (serviceAckReceiverDto.getLocationCode() != null) {
            serviceAckReceiver.setLocationCode(serviceAckReceiverDto.getLocationCode());
        }
        if (serviceAckReceiverDto.getEscalationId() != null) {
            serviceAckReceiver.setEscalationId(serviceAckReceiverDto.getEscalationId());
        }
        if (serviceAckReceiverDto.getActiveFlag() != null) {
            serviceAckReceiver.setActiveFlag(serviceAckReceiverDto.getActiveFlag());
        }*/
        //save and return
        return serviceAckReceiverMapper.ServiceAckReceiverToServiceAckReceiverDto(serviceAckReceiverRepository.save(serviceAckReceiver));
    }

    @Override
    public void deleteDtoById(int id) {
        try {
            serviceAckReceiverRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("ServiceAckReceiver not found");
        }
    }
}
