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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Tag(name = "equip-usage", description = "Equipment Usage API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(EquipUsageRequestController.BASE_URL)
public class EquipUsageRequestController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/equip-usage/requests";

    private final AuditorAware<String> auditorAware;

    private final EquipUsageRequestService equipUsageRequestService;

    // get all dto
    @Operation(summary = "Get list of EquipUsageRequestDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    //public List<EquipUsageRequestDto> getAllEquipUsageRequest(@RequestParam(required = false) Integer eamNo,
    public ResponseEntity<?> getAllEquipUsageRequest(@RequestParam(required = false) Integer eamNo,
                                                     @RequestParam(required = false) String caseNo,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd,
                                                     @RequestParam(required = false) String year,
                                                     @RequestParam(required = false) String month) {
        log.debug("get all equip usage request eamNo: " + eamNo + " caseNo: " + caseNo + " dateStart: "
                + dateStart + " dateEnd: " + dateEnd + " year: " + year + " month: " + month +
                " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        // check if month is not null, year is not null , return resp 400
        if (month != null && year == null) {
            return ResponseEntity.badRequest().body("year is required when month is not null");
        }
        // check dateStart is and dateEnd is not null, or dateStart and dateEnd is null
        if ((dateStart != null && dateEnd == null) || (dateStart == null && dateEnd != null)) {
            return ResponseEntity.badRequest().body("dateStart and dateEnd must be both null or not null");
        }
        return ResponseEntity.ok().body(this.equipUsageRequestService.getAllDto(eamNo, caseNo, dateStart, dateEnd, year, month));
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
