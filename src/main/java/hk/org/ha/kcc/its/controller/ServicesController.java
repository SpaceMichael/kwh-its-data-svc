package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.ServiceDto;
import hk.org.ha.kcc.its.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "service", description = "service API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ServicesController.BASE_URL)
public class ServicesController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/services";

    private final AuditorAware<String> auditorAware;

    private final ServiceService serviceService;

    // get all dto
    @Operation(summary = "Get list of ServiceDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceDto> getAllService() {
        log.debug("get all service by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceService.getAllDto();
    }

    // get dto by id
    @Operation(summary = "Get ServiceDto by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceDto getServiceById(@PathVariable Long id) {
        log.debug("get service by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceService.getDtoById(id);
    }

    // create dto
    @Operation(summary = "Create new ServiceDto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto createService(@RequestBody ServiceDto serviceDto) {
        log.debug("create service by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceService.create(serviceDto);
    }

    // update dto
    @Operation(summary = "Update ServiceDto")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceDto updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        log.debug("update service by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceService.updateById(id, serviceDto);
    }

    // delete dto
    @Operation(summary = "Delete ServiceDto")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteService(@PathVariable Long id) {
        log.debug("delete service by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        this.serviceService.deleteById(id);
    }

}
