package hk.org.ha.kcc.its.service;

import java.util.List;
import hk.org.ha.kcc.its.dto.MenuDto;

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
