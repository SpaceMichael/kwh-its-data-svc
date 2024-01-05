package hk.org.ha.kcc.its.service;


import hk.org.ha.kcc.its.dto.AlarmResponseDto;
import hk.org.ha.kcc.its.dto.alarm.AlarmDto;



public interface AlarmService {

    AlarmResponseDto create(AlarmDto alarmDto); // test use

}
