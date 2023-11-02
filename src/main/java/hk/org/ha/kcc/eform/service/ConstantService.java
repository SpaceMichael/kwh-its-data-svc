package hk.org.ha.kcc.eform.service;

import hk.org.ha.kcc.eform.dto.ConstantDto;

import java.util.List;

public interface ConstantService {

    ConstantDto create(ConstantDto constantDto);

    List<ConstantDto> getAllDto();

    ConstantDto getDtoById(Integer id);

    ConstantDto update(Integer id, ConstantDto constantDto);

    void delete(Integer id);
}
