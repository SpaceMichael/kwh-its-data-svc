package hk.org.ha.kcc.eform.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import org.springframework.web.bind.annotation.*;

import hk.org.ha.kcc.eform.dto.EformResponseDto;
import hk.org.ha.kcc.eform.service.EformService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.lang.invoke.MethodHandles;


@Tag(name = "e-Form", description = "e-Form API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(EformController.BASE_URL)
public class EformController {

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/eforms";

    private final EformService eformService;

    @GetMapping
    public EformResponseDto getEformList(@RequestParam(required = false) String qrcode){
        log.debug("qrcode: " + qrcode);
        System.out.println("qrcode: " + qrcode);
        return eformService.getEformList(qrcode);
    }

    /*
    Can just add qrcode parameter to the existing API:
            /api/vq/eforms?qrcode={QR CODE}
    And only return single form for “Bed Cleansing”.
*/

}
