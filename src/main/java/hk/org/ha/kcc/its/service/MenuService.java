package hk.org.ha.kcc.its.service;

import java.util.List;

import hk.org.ha.kcc.its.dto.EformDto;

public interface MenuService {

  // create new
  EformDto create(EformDto eformDto);

  // get All
  List<EformDto> getAllDto();

  // get by ID
  EformDto getDtoById(Integer id);

  // update by ID\
  EformDto updateById(Integer id, EformDto eformDto);

  // delete by ID
  void deleteById(Integer id);
}
