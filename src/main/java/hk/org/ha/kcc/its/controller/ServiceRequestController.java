package hk.org.ha.kcc.its.controller;


import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.ServiceRequestDto;
import hk.org.ha.kcc.its.service.ServiceRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "service-request", description = "Service Request API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ServiceRequestController.BASE_URL)
public class ServiceRequestController {


    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/services/requests";

    private final AuditorAware<String> auditorAware;

    private final ServiceRequestService serviceRequestService;

    // get all dto
    @Operation(summary = "Get list of ServiceRequestDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceRequestDto> getAllServiceRequest() {
        log.debug("get all service request by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceRequestService.getAllDto();
    }

    // get dto by id
    @Operation(summary = "Get ServiceRequestDto by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceRequestDto getServiceRequestById(@PathVariable String id) {
        log.debug("get service request by id: " + id + " by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceRequestService.getDtoById(id);
    }

    // create dto
    @Operation(summary = "Create new ServiceRequestDto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRequestDto createServiceRequest(
            @RequestBody ServiceRequestDto serviceRequestDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("create service request: " + serviceRequestDto + " by: " + currentAuditor);
        return this.serviceRequestService.create(serviceRequestDto);
    }

    // update dto
    @Operation(summary = "Update ServiceRequestDto")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceRequestDto updateServiceRequest(
            @PathVariable String id,
            @RequestBody ServiceRequestDto serviceRequestDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("update service request: " + serviceRequestDto + " by: " + currentAuditor);
        // check id is null
        if (serviceRequestDto.getId() == null) {
            serviceRequestDto.setId(id);
        }
        return this.serviceRequestService.updateById(id, serviceRequestDto);
    }

    // delete dto
    @Operation(summary = "Delete ServiceRequestDto")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteServiceRequest(@PathVariable String id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("delete service request id: " + id + " by: " + currentAuditor);
        this.serviceRequestService.deleteById(id);
    }


}
