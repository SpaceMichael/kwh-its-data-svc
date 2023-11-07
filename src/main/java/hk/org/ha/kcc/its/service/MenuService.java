package hk.org.ha.kcc.its.service;

import java.util.List;
import hk.org.ha.kcc.its.dto.MenuServiceDto;

public interface MenuService {

  // create new
  MenuServiceDto create(MenuServiceDto menuServiceDto);

  // get All
  List<MenuServiceDto> getAllDto();

  // get by ID
  MenuServiceDto getDtoById(Integer id);

  // update by ID\
  MenuServiceDto updateById(Integer id, MenuServiceDto menuServiceDto);

  // delete by ID
  void deleteById(Integer id);
}
