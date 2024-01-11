package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceDto;
import hk.org.ha.kcc.its.mapper.ServiceMapper;
import hk.org.ha.kcc.its.repository.ServiceRepository;
import hk.org.ha.kcc.its.model.Services;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public List<ServiceDto> getAllDto() {
        List<Services> servicesList = serviceRepository.findAll();
        return servicesList.stream().map(serviceMapper::ServiceToServiceDto).collect(Collectors.toList());
    }

    @Override
    public ServiceDto getDtoById(Integer id) {
        Services services = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        return serviceMapper.ServiceToServiceDto(services);
    }

    @Override
    public ServiceDto create(ServiceDto serviceDto) {
        Services services = serviceMapper.ServiceDtoToService(serviceDto);
        // save and return
        return serviceMapper.ServiceToServiceDto(serviceRepository.save(services));
    }

    @Override
    public ServiceDto updateById(Integer id, ServiceDto serviceDto) {
        Services services = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        // update the dto if not null
        services.setServiceCode(Optional.ofNullable(serviceDto.getServiceCode()).orElse(services.getServiceCode()));
        services.setServiceName(Optional.ofNullable(serviceDto.getServiceName()).orElse(services.getServiceName()));
        //save and return
        return serviceMapper.ServiceToServiceDto(serviceRepository.save(services));
    }

    @Override
    public void deleteById(Integer id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service not found");
        }
        serviceRepository.deleteById(id);
    }
}
