package hk.org.ha.kcc.its.service;

import org.springframework.http.ResponseEntity;
import hk.org.ha.kcc.its.dto.EformResponseDto;
import java.io.IOException;

public interface EformService {

  ResponseEntity<EformResponseDto> getEformList(String qrcode) throws IOException;
}
