package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.EquipUsageRequestDto;
import hk.org.ha.kcc.its.service.EquipUsageRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "equip-usage", description = "Equipment Usage API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(EquipUsageRequestController.BASE_URL)
public class EquipUsageRequestController {
    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/equip-usagee/requests";

    private final AuditorAware<String> auditorAware;

    private final EquipUsageRequestService equipUsageRequestService;

    // get all dto
    @Operation(summary = "Get list of EquipUsageRequestDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EquipUsageRequestDto> getAllEquipUsageRequest() {
        log.debug("get all equip usage request by: "
                + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.equipUsageRequestService.getAllDto();
    }

    // get dto by id
    @Operation(summary = "Get EquipUsageRequestDto by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EquipUsageRequestDto getEquipUsageRequestById(@PathVariable String id) {
        log.debug("get equip usage request by id: " + id + " by: "
                + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.equipUsageRequestService.getDtoById(id);
    }

    // create dto
    @Operation(summary = "Create new EquipUsageRequestDto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipUsageRequestDto createEquipUsageRequest(
            @RequestBody EquipUsageRequestDto equipUsageRequestDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("create equip usage request by: " + currentAuditor);
        // equipUsageRequestDto.(currentAuditor);
        return this.equipUsageRequestService.create(equipUsageRequestDto);
    }

    // update dto by id
    @Operation(summary = "Update EquipUsageRequestDto by id")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EquipUsageRequestDto updateEquipUsageRequest(@PathVariable String id,
            @RequestBody EquipUsageRequestDto equipUsageRequestDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("update equip usage request by: " + currentAuditor);
        return this.equipUsageRequestService.updateById(id, equipUsageRequestDto);
    }

    // delete by id
    @Operation(summary = "Delete EquipUsageRequestDto by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEquipUsageRequestById(@PathVariable String id) {
        log.debug("delete equip usage request by id: " + id + " by: "
                + auditorAware.getCurrentAuditor().orElse("Unknown"));
        this.equipUsageRequestService.deleteById(id);
    }

}
