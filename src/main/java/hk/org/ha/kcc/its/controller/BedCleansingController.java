package hk.org.ha.kcc.its.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import hk.org.ha.kcc.its.dto.BedCleansingDashBoardDto;
import hk.org.ha.kcc.its.dto.BedCleansingRequestAuditDto;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.service.BedCleansingRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "bed-cleansing", description = "bed-cleansing API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(BedCleansingController.BASE_URL)
public class BedCleansingController {

    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/bed-cleansing/requests";

    private final BedCleansingRequestService bedCleansingRequestService;

    private final AuditorAware<String> auditorAware;


    public BedCleansingController(BedCleansingRequestService bedCleansingRequestService,
                                  AuditorAware<String> auditorAware) {
        this.bedCleansingRequestService = bedCleansingRequestService;
        this.auditorAware = auditorAware;
    }

    // post BedCleansing
    @Operation(summary = "Create new BedCleansingRequest")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BedCleansingRequestDto createBCRequest(
            @RequestBody BedCleansingRequestDto bedCleansingRequestDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("create bed cleansing by: " + currentAuditor);
        bedCleansingRequestDto.setRequestor(currentAuditor);

        return this.bedCleansingRequestService.create(bedCleansingRequestDto);
    }

    // get all BedCleansing
    @Operation(summary = "Get list of BedCleansingRequest")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BedCleansingDashBoardDto getAllBCR(@RequestParam(required = false) String ward,
                                              @RequestParam(required = false) String cubicle,
                                              @RequestParam(required = false) String bed,
                                              @RequestParam(required = false) Integer period, // hour
                                              @RequestParam(required = false) Boolean completedStatus,
                                              @RequestParam(required = false) Boolean total) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("get all by: " + currentAuditor + " ward: " + ward + " cubicle: " + cubicle + "bed No: " + bed + " period: " + period + " status: " + completedStatus);
        List<BedCleansingRequestDto> bedCleansingRequestDtoList = this.bedCleansingRequestService.getAllDto(ward, cubicle, bed, period, completedStatus);
        if (total != null && total) {
            return BedCleansingDashBoardDto.builder().total(bedCleansingRequestDtoList.size()).bedCleansingRequestList(bedCleansingRequestDtoList).build();
        } else {
            return BedCleansingDashBoardDto.builder().total(null).bedCleansingRequestList(bedCleansingRequestDtoList).build();
        }
    }

    // get by id
    @Operation(summary = "Get the BedCleansingRequest by id")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BedCleansingRequestDto getBCRById(@PathVariable String id) {
        //public ResponseEntity<Map<String, Object>> getBCRequestById(@PathVariable String id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("get by id: " + id + " by: " + currentAuditor);
        return this.bedCleansingRequestService.getDtoById(id);
        /*Map<String, Object> response = new HashMap<>();
        response.put("data", this.bedCleansingRequestService.getDtoById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);*/
    }

    // patch by id
    @Operation(summary = "Update the BedCleansingRequest by id")
    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BedCleansingRequestDto updateBCRById(@PathVariable String id,
                                                @RequestBody BedCleansingRequestDto bedCleansingRequestDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("update by id: " + id + " by: " + currentAuditor);

        // if getStatus = Process || Completed , cleaner = currentAuditor
        if (bedCleansingRequestDto.getStatus() != null &&
                (bedCleansingRequestDto.getStatus().equalsIgnoreCase("Process") || bedCleansingRequestDto.getStatus().equalsIgnoreCase("Completed"))) {
            bedCleansingRequestDto.setCleaner(currentAuditor);
        }
        return this.bedCleansingRequestService.updateById(id, bedCleansingRequestDto);
    }

    // delete by id
    @Operation(summary = "Delete the BedCleansingRequest by id")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBCRById(@PathVariable String id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("delete by id: " + id + " by: " + currentAuditor);
        this.bedCleansingRequestService.deleteById(id);
    }

    // get detail by id
    @Operation(summary = "Get the BedCleansingRequest detail by id")
    @GetMapping(value = "/dtl/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BedCleansingRequestAuditDto> getBCRDtlById(@PathVariable String id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("get detail by id: " + id + " by: " + currentAuditor);
        return this.bedCleansingRequestService.getDtlByBCId(id);
    }

}
