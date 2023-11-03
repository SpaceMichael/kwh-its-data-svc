package hk.org.ha.kcc.its.service;

import java.util.List;
import hk.org.ha.kcc.its.dto.ConstantDto;

public interface ConstantService {

  ConstantDto create(ConstantDto constantDto);

  List<ConstantDto> getAllDto();

  ConstantDto getDtoById(Integer id);

  ConstantDto update(Integer id, ConstantDto constantDto);

  void delete(Integer id);
}
