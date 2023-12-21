package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.ServiceAckReceiverDto;
import hk.org.ha.kcc.its.model.ServiceAckReceiver;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ServiceAckReceiverMapper {

    ServiceAckReceiverDto ServiceAckReceiverToServiceAckReceiverDto(ServiceAckReceiver serviceAckReceiver);

    ServiceAckReceiver ServiceAckReceiverDtoToServiceAckReceiver(ServiceAckReceiverDto serviceAckReceiverDto);
}
