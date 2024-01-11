package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceAlarmReceiverDto;
import hk.org.ha.kcc.its.mapper.ServiceAlarmReceiverMapper;
import hk.org.ha.kcc.its.model.ServiceAlarmReceiver;
import hk.org.ha.kcc.its.repository.ServiceAlarmReceiverRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;

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
        serviceAlarmReceiverRepository.save(serviceAlarmReceiver);
        return serviceAlarmReceiverMapper.ServiceAlarmReceiverToServiceAlarmReceiverDto(serviceAlarmReceiver);
    }

    @Override
    public List<ServiceAlarmReceiverDto> getAllDto() {
        List<ServiceAlarmReceiver> serviceAlarmReceiverList = serviceAlarmReceiverRepository.findAll();
        return serviceAlarmReceiverList.stream().map(serviceAlarmReceiverMapper::ServiceAlarmReceiverToServiceAlarmReceiverDto).collect(Collectors.toList());
    }

    @Override
    public ServiceAlarmReceiverDto getDtoById(int id) {
        ServiceAlarmReceiver serviceAlarmReceiver = serviceAlarmReceiverRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ServiceAlarmReceiver not found"));
        return serviceAlarmReceiverMapper.ServiceAlarmReceiverToServiceAlarmReceiverDto(serviceAlarmReceiver);
    }

    @Override
    public ServiceAlarmReceiverDto updateDtoById(int id, ServiceAlarmReceiverDto serviceAlarmReceiverDto) {
        ServiceAlarmReceiver serviceAlarmReceiver = serviceAlarmReceiverRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ServiceAlarmReceiver not found"));
        BeanUtils.copyProperties(serviceAlarmReceiverDto, serviceAlarmReceiver, getNullPropertyNames(serviceAlarmReceiverDto));
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
