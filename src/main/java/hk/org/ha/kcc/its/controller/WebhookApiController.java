package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.alarm.AtWorkAlarmResponseDto;
import hk.org.ha.kcc.its.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@Tag(name = "webhook", description = "Webhook Callback API")
@SecurityRequirement(name = "api-key")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(WebhookApiController.BASE_URL)
public class WebhookApiController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    public static final String BASE_URL = "/api/v2/webhook";

    private final AlarmService alarmService;

    @Operation(summary = "PATCH alarm From Webhook ITS")
    @PostMapping(value = "/alarm", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void webhookAlarm(@RequestBody AtWorkAlarmResponseDto atWorkAlarmResponseDto) {
        log.debug("atWorkAlarmResponseDto:", atWorkAlarmResponseDto);
        alarmService.webhookAlarm(atWorkAlarmResponseDto);
    }
}
