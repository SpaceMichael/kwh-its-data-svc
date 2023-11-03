package hk.org.ha.kcc.its.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import hk.org.ha.kcc.its.dto.ConstantDto;
import hk.org.ha.kcc.its.model.Constant;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface ConstantMapper {

    ConstantDto ConstantToConstantDto(Constant constant);

    Constant ConstantDtoToConstant(ConstantDto constantDto);
}
