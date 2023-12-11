package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceDto;

import java.util.List;

public interface ServiceService {
    // get all dto
    List<ServiceDto> getAllDto();

    // get dto by id
    ServiceDto getDtoById(Integer id);

    // create dto
    ServiceDto create(ServiceDto serviceDto);

    // update dto by id
    ServiceDto updateById(Integer id, ServiceDto serviceDto);

    // delete by id
    void deleteById(Integer id);
}
