package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.EquipUsageRequestDto;
import hk.org.ha.kcc.its.model.EquipUsageRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface EquipUsageRequestMapper {

    EquipUsageRequestDto EquipUsageRequestToEquipUsageRequestDto(
            EquipUsageRequest equipUsageRequest);

    EquipUsageRequest EquipUsageRequestDtoToEquipUsageRequest(
            EquipUsageRequestDto equipUsageRequestDto);


}
