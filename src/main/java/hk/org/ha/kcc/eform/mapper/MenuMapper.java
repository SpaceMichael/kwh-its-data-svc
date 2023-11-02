package hk.org.ha.kcc.eform.mapper;

import hk.org.ha.kcc.eform.dto.MenuDto;
import hk.org.ha.kcc.eform.model.Menu;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface MenuMapper {
    MenuDto MenuToMenuDto(Menu menu);

    Menu MenuDtoToMenu(MenuDto menuDto);
}
