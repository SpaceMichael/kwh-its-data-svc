package hk.org.ha.kcc.its.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.EformResponseDto;
import hk.org.ha.kcc.its.service.EformService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "e-Form", description = "e-Form API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(EformController.BASE_URL)
public class EformController {

  private static final AlsXLogger log =
      AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
  public static final String BASE_URL = "/api/v1/eforms";

  private final EformService eformService;

  @GetMapping
  public ResponseEntity<EformResponseDto> getEformList(
      @RequestParam(required = false) String qrcode) throws IOException {
    log.debug("getEformList qrcode: " + qrcode);
    //System.out.println("getEformList qrcode: " + qrcode);
    return eformService.getEformList(qrcode);
  }

  /*
   * Can just add qrcode parameter to the existing API: /api/vq/eforms?qrcode={QR CODE} And only
   * return single form for “Bed Cleansing”.
   */
}
