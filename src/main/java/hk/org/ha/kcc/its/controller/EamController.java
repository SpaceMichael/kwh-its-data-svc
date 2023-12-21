package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.EamDto;
import hk.org.ha.kcc.its.service.EamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "eam", description = "eam API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(EamController.BASE_URL)
public class EamController {
    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/eam";

    private final AuditorAware<String> auditorAware;

    private final EamService eamService;

    // get all eam
    @Operation(summary = "Get list of Eam")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EamDto> getAllEam() {
        log.debug("get all eam by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.eamService.getAllDto();
    }

    // get eam by id
    @Operation(summary = "Get Eam by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EamDto getEamById(@PathVariable Integer id) {
        log.debug("get eam by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.eamService.getDtoById(id);
    }

    // create eam
    @Operation(summary = "Create Eam")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EamDto createEam(@RequestBody EamDto eamDto) {
        log.debug("create eam: " + eamDto + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.eamService.create(eamDto);
    }

/*    // update eam
    @Operation(summary = "Update Eam")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EamDto updateEam(@PathVariable Integer id, @RequestBody EamDto eamDto) {
        log.debug("update eam: " + eamDto + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.eamService.updateById(id, eamDto);
    }

    // delete eam
    @Operation(summary = "Delete Eam")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEam(@PathVariable Integer id) {
        log.debug("delete eam by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        this.eamService.deleteById(id);
    }*/
}
