package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.dto.AlarmResponseDto;

public interface AlarmService {

    AlarmResponseDto create(AlarmDto alarmDto); // test use

    AlarmResponseDto createAlram(String id, String alarmCode, String locationCode, String severity, String type, String title, String message, Integer ackThreshold, Boolean webhook, Boolean notificationRequired);

}
