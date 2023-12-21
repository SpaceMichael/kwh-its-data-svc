package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.ServiceAckReceiverDto;
import hk.org.ha.kcc.its.service.ServiceAckReceiverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "service-ack-receiver", description = "service-ack-receiver API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ServiceAckReceiverController.BASE_URL)
public class ServiceAckReceiverController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/service-ack-receiver";
    private final AuditorAware<String> auditorAware;
    private final ServiceAckReceiverService serviceAckReceiverService;

    // get all dto
    @Operation(summary = "Get list of ServiceAckReceiverDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceAckReceiverDto> getAllServiceAckReceiver() {
        log.debug("get all service ack receiver by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAckReceiverService.getAllDto();
    }

/*
    //create dto
    @Operation(summary = "Create new ServiceAckReceiverDto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceAckReceiverDto createServiceAckReceiver(@RequestBody ServiceAckReceiverDto serviceAckReceiverDto) {
        log.debug("create service ack receiver by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAckReceiverService.create(serviceAckReceiverDto);
    }
*/

/*    // get dto by id
    @Operation(summary = "Get ServiceAckReceiverDto by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceAckReceiverDto getServiceAckReceiverById(@PathVariable Integer id) {
        log.debug("get service ack receiver by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAckReceiverService.getDtoById(id);
    }

    // update dto by id
    @Operation(summary = "Update ServiceAckReceiverDto by id")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceAckReceiverDto updateServiceAckReceiverById(@PathVariable Integer id, @RequestBody ServiceAckReceiverDto serviceAckReceiverDto) {
        log.debug("update service ack receiver by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAckReceiverService.updateDtoById(id, serviceAckReceiverDto);
    }

    // delete dto by id
    @Operation(summary = "Delete ServiceAckReceiverDto by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteServiceAckReceiverById(@PathVariable Integer id) {
        log.debug("delete service ack receiver by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        this.serviceAckReceiverService.deleteDtoById(id);
    }*/
}
