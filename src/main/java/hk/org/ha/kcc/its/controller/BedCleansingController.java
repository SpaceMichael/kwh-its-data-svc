package hk.org.ha.kcc.its.controller;

import java.lang.invoke.MethodHandles;

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

    // get all
    @Operation(summary = "Get list of BedCleansingRequest")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<BedCleansingRequestDto> getAllBCRequest(@RequestParam(required = false) String ward,
                                                            @RequestParam(required = false) String cubicle,
                                                            @RequestParam(required = false) String bed) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("get all by: " + currentAuditor + " ward: " + ward + " cubicle: " + cubicle + "bed No: " + bed);
        return this.bedCleansingRequestService.getAllDto(ward, cubicle, bed);
    }

    // get by id
    @Operation(summary = "Get the BedCleansingRequest by id")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BedCleansingRequestDto getBCRequestById(@PathVariable String id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("get by id: " + id + " by: " + currentAuditor);

        return this.bedCleansingRequestService.getDtoById(id);
    }

    // patch by id
    @Operation(summary = "Update the BedCleansingRequest by id")
    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BedCleansingRequestDto updateBCRequestById(@PathVariable String id,
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
    public void deleteBCRequestById(@PathVariable String id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("delete by id: " + id + " by: " + currentAuditor);
        this.bedCleansingRequestService.deleteById(id);
    }
}
