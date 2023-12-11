package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EamDto;

import java.util.List;

public interface EamService {
    // get all dto
    List<EamDto> getAllDto();

    // get dto by Id
    EamDto getDtoById(Long id);

    // create dto
    EamDto create(EamDto eamDto);

    // update by Id
    EamDto updateById(Long id, EamDto eamDto);

    // delete by Id
    void deleteById(Long id);

}
