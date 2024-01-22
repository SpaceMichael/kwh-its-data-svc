package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceAlarmSenderDto;

import java.util.List;

public interface ServiceAlarmSenderService {
    // create dto
    ServiceAlarmSenderDto create(ServiceAlarmSenderDto dto);

    // get all dto
    List<ServiceAlarmSenderDto> getAllDto();

    // get dto by id
    ServiceAlarmSenderDto getDtoById(int id);

    // update dto by id
    ServiceAlarmSenderDto updateDtoById(int id, ServiceAlarmSenderDto dto);

    // delete dto by id
    void deleteDtoById(int id);
}
