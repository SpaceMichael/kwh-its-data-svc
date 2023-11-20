package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.EformDto;
import hk.org.ha.kcc.its.model.Eform;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface EformMapper {

  EformDto EformToEformDto(Eform eform);

  Eform EformDtoToEform(EformDto eformDto);
}
