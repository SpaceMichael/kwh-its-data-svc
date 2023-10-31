package hk.org.ha.kcc.eform.service;

import hk.org.ha.kcc.eform.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.eform.model.BedCleansingRequest;

import java.util.List;


public interface BedCleansingRequestService {
    BedCleansingRequestDto create(BedCleansingRequestDto bedCleansingRequestDto);

    List<BedCleansingRequestDto> getAllDto();

    BedCleansingRequestDto getDtoById(String id);

    BedCleansingRequestDto update(String id, BedCleansingRequestDto bedCleansingRequestDto);

    void delete(String id);
}
