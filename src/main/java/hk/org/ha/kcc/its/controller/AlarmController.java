package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.service.AlarmService;
import hk.org.ha.kcc.its.service.BedCleansingRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@Tag(name = "alarm", description = "alarm API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(AlarmController.BASE_URL)
public class AlarmController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/alarms";

    private final AlarmService alarmService;

    private final AuditorAware<String> auditorAware;

    public AlarmController(AlarmService alarmService, AuditorAware<String> auditorAware) {
        this.alarmService = alarmService;
        this.auditorAware = auditorAware;
    }

    // create alarm
    @Operation(summary = "Create new Alarm call ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAlarm() {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("test create alarm by: " + currentAuditor);
        AlarmDto alarmDto = new AlarmDto();
        alarmService.create(alarmDto);
    }

    // create alarm
    @Operation(summary = "Create new Alarm in DB")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAlarm(@RequestBody AlarmDto alarmDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("create alarm: " + alarmDto + " in db by: " + currentAuditor);
        alarmService.createDto(alarmDto);
    }
}
