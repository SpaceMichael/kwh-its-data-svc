package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.ServiceRequestDto;
import hk.org.ha.kcc.its.model.ServiceRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;



@Mapper(builder = @Builder(disableBuilder = true))
public interface ServiceRequestMapper {

    ServiceRequestDto ServiceRequestToServiceRequestDto(
            ServiceRequest serviceRequest);

    ServiceRequest ServiceRequestDtoToServiceRequest(
            ServiceRequestDto serviceRequestDto);
}
