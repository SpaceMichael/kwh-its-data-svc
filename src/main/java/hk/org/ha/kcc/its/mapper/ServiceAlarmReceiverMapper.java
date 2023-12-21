package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.ServiceAlarmReceiverDto;
import hk.org.ha.kcc.its.model.ServiceAlarmReceiver;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ServiceAlarmReceiverMapper {

    ServiceAlarmReceiverDto ServiceAlarmReceiverToServiceAlarmReceiverDto(ServiceAlarmReceiver serviceAlarmReceiver);

    ServiceAlarmReceiver ServiceAlarmReceiverDtoToServiceAlarmReceiver(ServiceAlarmReceiverDto serviceAlarmReceiverDto);
}
