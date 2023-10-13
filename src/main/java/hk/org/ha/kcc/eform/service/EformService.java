package hk.org.ha.kcc.eform.service;

import hk.org.ha.kcc.eform.dto.EformResponseDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface EformService {
    ResponseEntity<EformResponseDto> getEformList(String qrcode) throws IOException;
}
