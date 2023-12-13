package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.EamDto;
import hk.org.ha.kcc.its.model.Eam;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;



@Mapper(builder = @Builder(disableBuilder = true))
public interface EamMapper {
    // Eam to EamDto
    EamDto EamToEamDto(Eam eam);

    // EamDto to Eam
    Eam EamDtoToEam(EamDto eamDto);
}
