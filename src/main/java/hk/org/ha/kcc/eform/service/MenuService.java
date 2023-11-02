package hk.org.ha.kcc.eform.service;

import hk.org.ha.kcc.eform.dto.MenuDto;

import java.util.List;

public interface MenuService {

    // create new
    MenuDto create(MenuDto menuDto);

    // get All
    List<MenuDto> getAllDto();

    // get by ID
    MenuDto getDtoById(Integer id);

    // update by ID\
    MenuDto update(Integer id, MenuDto menuDto);

    // delete by ID
    void delete(Integer id);

}
