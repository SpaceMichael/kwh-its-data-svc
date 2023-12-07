package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.ServiceDto;
import hk.org.ha.kcc.its.model.Services;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface ServiceMapper {
  // service mapper serviceDto
    Services ServiceDtoToService(
            ServiceDto serviceDto);

    ServiceDto ServiceToServiceDto(
            Services services);

}
