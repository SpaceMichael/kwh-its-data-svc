package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceAckReceiverDto;

import java.util.List;

public interface ServiceAckReceiverService {
    // create dto
    ServiceAckReceiverDto create(ServiceAckReceiverDto dto);

    // get all dto
    List<ServiceAckReceiverDto> getAllDto();

    // get dto by id
    ServiceAckReceiverDto getDtoById(int id);

    // update dto by id
    ServiceAckReceiverDto updateDtoById(int id, ServiceAckReceiverDto dto);

    // delete dto by id
    void deleteDtoById(int id);
}
