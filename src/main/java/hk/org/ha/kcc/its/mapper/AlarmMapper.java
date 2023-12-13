package hk.org.ha.kcc.its.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import hk.org.ha.kcc.its.model.Alarm;
import hk.org.ha.kcc.its.dto.AlarmDto;


@Mapper(builder = @Builder(disableBuilder = true))
public interface AlarmMapper {

    AlarmDto AlarmToAlarmDto(Alarm alarm);

    Alarm AlarmDtoToAlarm(AlarmDto alarmDto);
}
