package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.ServiceAlarmReceiverDto;
import hk.org.ha.kcc.its.service.ServiceAlarmReceiverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "service-alarm-receiver", description = "service-alarm-receiver API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ServiceAlarmReceiverController.BASE_URL)
public class ServiceAlarmReceiverController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/service-alarm-receiver";
    private final AuditorAware<String> auditorAware;
    private final ServiceAlarmReceiverService serviceAlarmReceiverService;

    // get all dto
    @Operation(summary = "Get list of ServiceAlarmReceiverDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceAlarmReceiverDto> getAllServiceAlarmReceiver() {
        log.debug("get all service alarm receiver by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAlarmReceiverService.getAllDto();
    }

/*    //create dto
    @Operation(summary = "Create new ServiceAlarmReceiverDto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceAlarmReceiverDto createServiceAlarmReceiver(@RequestBody ServiceAlarmReceiverDto serviceAlarmReceiverDto) {
        log.debug("create service alarm receiver by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAlarmReceiverService.createDto(serviceAlarmReceiverDto);
    }

    // get dto by id
    @Operation(summary = "Get ServiceAlarmReceiverDto by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceAlarmReceiverDto getServiceAlarmReceiverById(@PathVariable Integer id) {
        log.debug("get service alarm receiver by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAlarmReceiverService.getDtoById(id);
    }

    // update dto by id
    @Operation(summary = "Update ServiceAlarmReceiverDto by id")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceAlarmReceiverDto updateServiceAlarmReceiverById(@PathVariable Integer id, @RequestBody ServiceAlarmReceiverDto serviceAlarmReceiverDto) {
        log.debug("update service alarm receiver by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAlarmReceiverService.updateDtoById(id, serviceAlarmReceiverDto);
    }

    //delete by id
    @Operation(summary = "Delete ServiceAlarmReceiverDto by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteServiceAlarmReceiverById(@PathVariable Integer id) {
        log.debug("delete service alarm receiver by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        this.serviceAlarmReceiverService.delDtoById(id);
    }*/
}
