package hk.org.ha.kcc.its.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import hk.org.ha.kcc.its.dto.EformDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    private final AuditorAware<String> auditorAware;

    @GetMapping
    public ResponseEntity<EformResponseDto> getEformList(
            @RequestParam(required = false) String qrcode) throws IOException {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("getEformList qrcode: " + qrcode + " user " + currentAuditor);
        //System.out.println("getEformList qrcode: " + qrcode);
        return eformService.getEformList(qrcode,currentAuditor);
    }

    /*
     * Can just add qrcode parameter to the existing API: /api/vq/eforms?qrcode={QR CODE} And only
     * return single form for “Bed Cleansing”.
     */
    // post eform service
    @Operation(summary = "Create new eform service")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EformDto createEform(@RequestBody EformDto eformDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("createEform by : " + currentAuditor);
        return this.eformService.create(eformDto);
    }

    // patch eform service by id
    @Operation(summary = "Update eform service by ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EformDto updateEform(@PathVariable Long id, @RequestBody EformDto eformDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("updateEform id: " + id + " by : " + currentAuditor);
        return this.eformService.updateById(id, eformDto);
    }

    // get eform service by all
    @Operation(summary = "Get all eform service")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<EformDto> getAllEform() {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("getAllEform by : " + currentAuditor);
        return this.eformService.getAllDto();
    }

    // get eform service by id
    @Operation(summary = "Get eform service by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EformDto getEformById(@PathVariable Long id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("getEformById id:" + id + " by : " + currentAuditor);
        return this.eformService.getDtoById(id);
    }

    // delete eform service by id
    @Operation(summary = "delete eform service by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEform(@PathVariable Long id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("deleteEform id: " + id + " by : " + currentAuditor);
        this.eformService.deleteById(id);
    }

}
