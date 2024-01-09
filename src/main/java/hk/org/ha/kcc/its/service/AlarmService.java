package hk.org.ha.kcc.its.service;


import hk.org.ha.kcc.its.dto.AlarmResponseDto;
import hk.org.ha.kcc.its.dto.alarm.AlarmDto;
import hk.org.ha.kcc.its.dto.alarm.AtWorkAlarmResponseDto;


public interface AlarmService {

    AtWorkAlarmResponseDto create(AlarmDto alarmDto); // test use

    void webhookAlarm(AtWorkAlarmResponseDto atWorkAlarmResponseDto);

}
