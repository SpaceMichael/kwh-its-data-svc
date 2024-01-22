package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.ServiceAlarmSenderDto;
import hk.org.ha.kcc.its.service.ServiceAlarmSenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "service-ack-receiver", description = "service-ack-receiver API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ServiceAlarmSenderController.BASE_URL)
public class ServiceAlarmSenderController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/service-ack-receiver";
    private final AuditorAware<String> auditorAware;
    private final ServiceAlarmSenderService serviceAlarmSenderService;

    // get all dto
    @Operation(summary = "Get list of ServiceAckReceiverDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceAlarmSenderDto> getAllServiceAckReceiver() {
        log.debug("get all service ack receiver by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.serviceAlarmSenderService.getAllDto();
    }

}
