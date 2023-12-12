package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.dto.AlarmResponseDto;

import java.util.List;

public interface AlarmService {

    AlarmResponseDto create(AlarmDto alarmDto); // test use


    // get dto by SR id
    AlarmDto getDtoBySRId(Integer id, String location);

    // create dto
    AlarmDto createDto(AlarmDto alarmDto);

    // get all dto
    List<AlarmDto> getAllDto();

    // update dto by id
    AlarmDto updateDtoById(Integer id, AlarmDto alarmDto);


}
