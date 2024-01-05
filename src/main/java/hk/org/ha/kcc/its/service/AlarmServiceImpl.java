package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;

import hk.org.ha.kcc.its.dto.AlarmResponseDto;
import hk.org.ha.kcc.its.dto.alarm.AlarmDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.invoke.MethodHandles;


@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {

    @Value("${app.its.alarm.path}")
    private String alarmPath;
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());



    @Override
    public AlarmResponseDto create(AlarmDto alarmDto) {

        String path = alarmPath + "api/v1/alarms";
        WebClient webClient = WebClient.create(alarmPath + "api/v1/alarms");
        // get the current user token
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // test String
        String payload = "{\"ackThreshold\":null,"
                + "\"alarmType\":\"" + alarmDto.getType() + "\","
                + "\"escalationId\":" + alarmDto.getToEscalationId() + ","
                + "\"severity\":null,"
                + "\"title\":null,"
                + "\"message\":\"" + alarmDto.getMessage() + "\","
                + "\"webhook\":null}";

        if (alarmDto.getAckThreshold() != null) {
            payload = payload.replace("\"ackThreshold\":null", "\"ackThreshold\":\"" + alarmDto.getAckThreshold() + "\"");
        }
        if (alarmDto.getSeverity() != null) {
            payload = payload.replace("\"severity\":null", "\"severity\":\"" + alarmDto.getSeverity() + "\"");
        }
        if (alarmDto.getTitle() != null) {
            payload = payload.replace("\"title\":null", "\"title\":\"" + alarmDto.getTitle() + "\"");
        }
        if (alarmDto.getWebhook() != null) {
            payload = payload.replace("\"webhook\":null", "\"webhook\":" + alarmDto.getWebhook());
        }
        // check payload
        log.debug("payload: " + payload);


        // add the token and alarmDto1 to request body
        AlarmResponseDto alarmResponseDto = webClient.post()
                .uri(path)
                .headers(h -> h.setBearerAuth(jwt.getTokenValue()))
                .bodyValue(alarmDto)
                .retrieve()
                .bodyToMono(AlarmResponseDto.class)
                .block();
        log.debug("alarmResponseDto: " + alarmResponseDto);
        return alarmResponseDto;
    }

}
