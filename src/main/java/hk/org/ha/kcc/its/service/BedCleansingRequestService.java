package hk.org.ha.kcc.its.service;

import java.util.List;

import hk.org.ha.kcc.its.dto.BedCleansingDashBoardDto;
import hk.org.ha.kcc.its.dto.BedCleansingRequestAuditDto;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;

public interface BedCleansingRequestService {

    BedCleansingRequestDto create(BedCleansingRequestDto bedCleansingRequestDto);

    List<BedCleansingRequestDto> getAllDto(String ward, String cubicle, String bed, Integer period, Boolean completedStatus);

    BedCleansingRequestDto getDtoById(String id);

    BedCleansingRequestDto updateById(String id, BedCleansingRequestDto bedCleansingRequestDto);

    void deleteById(String id);

    // get the detail of BedCleansing request by ID
    List<BedCleansingRequestAuditDto> getDtlByBCId(String Id);

    BedCleansingDashBoardDto getBedCleansingDashBoardDto(String ward, String cubicle, String bed, Integer period, Boolean completedStatus, Boolean total);

}
