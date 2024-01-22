package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceAlarmSenderDto;
import hk.org.ha.kcc.its.mapper.ServiceAlarmSenderMapper;
import hk.org.ha.kcc.its.model.ServiceAlarmSender;
import hk.org.ha.kcc.its.repository.ServiceAlarmSenderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;

@Service
@Transactional
public class ServiceAlarmSenderServiceImpl implements ServiceAlarmSenderService {
    private final ServiceAlarmSenderRepository serviceAlarmSenderRepository;
    private final ServiceAlarmSenderMapper serviceAlarmSenderMapper;

    public ServiceAlarmSenderServiceImpl(ServiceAlarmSenderRepository serviceAlarmSenderRepository, ServiceAlarmSenderMapper serviceAlarmSenderMapper) {
        this.serviceAlarmSenderRepository = serviceAlarmSenderRepository;
        this.serviceAlarmSenderMapper = serviceAlarmSenderMapper;
    }

    @Override
    public ServiceAlarmSenderDto create(ServiceAlarmSenderDto serviceAlarmSenderDto) {
        ServiceAlarmSender serviceAlarmSender = serviceAlarmSenderMapper.ServiceAckReceiverDtoToServiceAckReceiver(serviceAlarmSenderDto);
        return serviceAlarmSenderMapper.ServiceAckReceiverToServiceAckReceiverDto(serviceAlarmSenderRepository.save(serviceAlarmSender));
    }

    @Override
    public List<ServiceAlarmSenderDto> getAllDto() {
        List<ServiceAlarmSender> serviceAlarmSenderList = serviceAlarmSenderRepository.findAll();
        return serviceAlarmSenderList.stream().map(serviceAlarmSenderMapper::ServiceAckReceiverToServiceAckReceiverDto).collect(Collectors.toList());
    }

    @Override
    public ServiceAlarmSenderDto getDtoById(int id) {
        ServiceAlarmSender serviceAlarmSender = serviceAlarmSenderRepository.findById(id).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("ServiceAckReceiver not found"));
        return serviceAlarmSenderMapper.ServiceAckReceiverToServiceAckReceiverDto(serviceAlarmSender);
    }

    @Override
    public ServiceAlarmSenderDto updateDtoById(int id, ServiceAlarmSenderDto serviceAlarmSenderDto) {
        ServiceAlarmSender serviceAlarmSender = serviceAlarmSenderRepository.findById(id).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("ServiceAckReceiver not found"));
        BeanUtils.copyProperties(serviceAlarmSenderDto, serviceAlarmSender, getNullPropertyNames(serviceAlarmSenderDto));
        //save and return
        return serviceAlarmSenderMapper.ServiceAckReceiverToServiceAckReceiverDto(serviceAlarmSenderRepository.save(serviceAlarmSender));
    }

    @Override
    public void deleteDtoById(int id) {
        try {
            serviceAlarmSenderRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("ServiceAckReceiver not found");
        }
    }
}
