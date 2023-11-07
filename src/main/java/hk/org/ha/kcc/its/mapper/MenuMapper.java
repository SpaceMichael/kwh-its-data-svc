package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.MenuDto;
import hk.org.ha.kcc.its.model.Menu;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface MenuMapper {

  MenuDto MenuToMenuDto(Menu menu);

  Menu MenuDtoToMenu(MenuDto menuDto);
}
