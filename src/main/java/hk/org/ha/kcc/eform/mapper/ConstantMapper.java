package hk.org.ha.kcc.eform.mapper;

import hk.org.ha.kcc.eform.dto.ConstantDto;
import hk.org.ha.kcc.eform.model.Constant;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface ConstantMapper {

    ConstantDto ConstantToConstantDto(Constant constant);
    Constant ConstantDtoToConstant(ConstantDto constantDto);
}
