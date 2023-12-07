package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceRequestDto;

import java.util.List;

public interface ServiceRequestService {

    //get all dto
    List<ServiceRequestDto> getAllDto();

    //get dto by id
    ServiceRequestDto getDtoById(String id);

    //create by id
    ServiceRequestDto create(ServiceRequestDto serviceRequestDto);

    //update dto by id
    ServiceRequestDto updateById(String id, ServiceRequestDto serviceRequestDto);

    //delete dto by id
    void deleteById(String id);
}
