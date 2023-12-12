package hk.org.ha.kcc.its.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import hk.org.ha.kcc.its.model.Alarm;
import hk.org.ha.kcc.its.dto.AlarmDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface AlarmMapper {
    // Alarm to AlarmDto
    AlarmDto AlarmToAlarmDto(Alarm alarm);

    Alarm AlarmDtoToAlarm(AlarmDto alarmDto);
}
