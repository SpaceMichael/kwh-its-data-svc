package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EquipUsageRequestDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EquipUsageRequestService {
    EquipUsageRequestDto create(EquipUsageRequestDto equipUsageRequestDto);

    List<EquipUsageRequestDto> getAllDto(Integer eamNo, String caseNo, Date dateStart, Date dateEnd, String year, String month);

    EquipUsageRequestDto getDtoById(String id);

    EquipUsageRequestDto updateById(String id, EquipUsageRequestDto equipUsageRequestDto);

    void deleteById(String id);


}
