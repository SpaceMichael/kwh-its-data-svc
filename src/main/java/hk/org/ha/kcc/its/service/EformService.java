package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EformDto;
import hk.org.ha.kcc.its.model.Eform;
import org.springframework.http.ResponseEntity;
import hk.org.ha.kcc.its.dto.EformResponseDto;

import java.io.IOException;
import java.util.List;

public interface EformService {
    ResponseEntity<EformResponseDto> getEformList(String qrcode, String currentAuditor) throws IOException;

    EformDto create(EformDto eformDto);

    // get All
    List<EformDto> getAllDto();

    // get by ID
    EformDto getDtoById(Long id);

    // update by ID\
    EformDto updateById(Long id, EformDto eformDto);

    // delete by ID
    void deleteById(Long id);

    // get the ID by qrcodeType
    List<EformDto> findByQrcodeType(String qrcodeType);
}
