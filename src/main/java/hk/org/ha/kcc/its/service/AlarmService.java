package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.dto.AlarmResponseDto;

public interface AlarmService {

    AlarmResponseDto create(AlarmDto alarmDto);

}
