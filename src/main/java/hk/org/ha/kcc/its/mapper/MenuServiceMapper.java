package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.MenuServiceDto;
import hk.org.ha.kcc.its.model.MenuService;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface MenuServiceMapper {

  MenuServiceDto MenuToMenuDto(MenuService menuService);

  MenuService MenuDtoToMenu(MenuServiceDto menuServiceDto);
}
