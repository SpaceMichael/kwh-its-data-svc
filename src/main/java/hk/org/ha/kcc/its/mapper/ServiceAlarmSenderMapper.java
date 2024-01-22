package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.ServiceAlarmSenderDto;
import hk.org.ha.kcc.its.model.ServiceAlarmSender;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ServiceAlarmSenderMapper {

    ServiceAlarmSenderDto ServiceAckReceiverToServiceAckReceiverDto(ServiceAlarmSender serviceAlarmSender);

    ServiceAlarmSender ServiceAckReceiverDtoToServiceAckReceiver(ServiceAlarmSenderDto serviceAlarmSenderDto);
}
