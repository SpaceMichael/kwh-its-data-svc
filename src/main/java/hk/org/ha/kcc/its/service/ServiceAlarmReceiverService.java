package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.ServiceAlarmReceiverDto;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface ServiceAlarmReceiverService {
    // create dto
    ServiceAlarmReceiverDto createDto(ServiceAlarmReceiverDto dto);

    // get all dto
    List<ServiceAlarmReceiverDto> getAllDto();

    // get dto by id
    ServiceAlarmReceiverDto getDtoById(int id);

    // update dto by id
    ServiceAlarmReceiverDto updateDtoById(int id, ServiceAlarmReceiverDto dto);

    // del dto by id
    void delDtoById(int id);
}
