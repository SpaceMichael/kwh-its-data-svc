package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EquipUsageRequestDto;

import java.util.List;

public interface EquipUsageRequestService {
    EquipUsageRequestDto create(EquipUsageRequestDto equipUsageRequestDto);

    List<EquipUsageRequestDto> getAllDto();

    EquipUsageRequestDto getDtoById(String id);

    EquipUsageRequestDto updateById(String id, EquipUsageRequestDto equipUsageRequestDto);

    void deleteById(String id);


}
