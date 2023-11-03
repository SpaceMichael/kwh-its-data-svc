package hk.org.ha.kcc.its.service;

import java.util.List;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;

public interface BedCleansingRequestService {

  BedCleansingRequestDto create(BedCleansingRequestDto bedCleansingRequestDto);

  List<BedCleansingRequestDto> getAllDto();

  BedCleansingRequestDto getDtoById(String id);

  BedCleansingRequestDto update(String id, BedCleansingRequestDto bedCleansingRequestDto);

  void delete(String id);
}
