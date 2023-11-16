package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.service.BedCleansingRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@Tag(name = "bed-cleansing", description = "bed-cleansing API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(BedCleansingDBController.BASE_URL)
public class BedCleansingDBController {
    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v2/bed-cleansing/requests";

    private final BedCleansingRequestService bedCleansingRequestService;


    public BedCleansingDBController(BedCleansingRequestService bedCleansingRequestService) {
        this.bedCleansingRequestService = bedCleansingRequestService;
    }

    // get all
    @Operation(summary = "Get list of BedCleansingRequest")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<BedCleansingRequestDto> getAllBCR(@RequestParam(required = false) String ward,
                                                      @RequestParam(required = false) String cubicle,
                                                      @RequestParam(required = false) String bed,
                                                      @RequestParam(required = false) Integer period,
                                                      @RequestParam(required = false) Boolean completedStatus) {
        log.debug("get all " + " ward: " + ward + " cubicle: " + cubicle + "bed No: " + bed + " period: " + period + " status: " + completedStatus);
        return this.bedCleansingRequestService.getAllDto(ward, cubicle, bed, period, completedStatus);
    }
}
