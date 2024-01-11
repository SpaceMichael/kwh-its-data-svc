package hk.org.ha.kcc.its.service;


import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;

import hk.org.ha.kcc.its.dto.alarm.AlarmDto;
import hk.org.ha.kcc.its.dto.alarm.AtWorkAlarmResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.lang.invoke.MethodHandles;


@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {

    @Value("${app.its.alarm.path}")
    private String alarmPath;
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    private final WebClient kwhItsWebClient;

    public AlarmServiceImpl(WebClient kwhItsWebClient) {
        this.kwhItsWebClient = kwhItsWebClient;
    }


    @Override
    public AtWorkAlarmResponseDto create(AlarmDto alarmDto) {

        /*String path = alarmPath + "api/v1/alarms";
        WebClient webClient = WebClient.create(alarmPath + "api/v1/alarms");
        // get the current user token
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/
        // add payload from alarmDto
        String payload = "{\n" +
                "\"message\": \"" + alarmDto.getMessage() + "\",\n" +
                "\"escalationId\": \"" + alarmDto.getEscalationId() + "\",\n" +
                "\"alarmType\": \"" + alarmDto.getAlarmType() + "\",\n" +
                "\"severity\":\"null\",\n" +
                "\"webhook\":\"true\",\n" +
                "\"title\": \"" + alarmDto.getTitle() + "\"\n" +
                "}";
        /* ok sample
                String payload2 ="{\"message\": \"1258\", \n" +
                "\"escalationId\": \"73\",\n" +
                "\"alarmType\": \"Houseman\",\n" +
                "\"severity\":\"normal\",\n" +
                "\"title\": \"test title\"\n" +
                "}";
         */
        // check payload
        log.debug("payload: " + payload);
        // send the post request to hkt alarm
        AtWorkAlarmResponseDto atWorkAlarmResponseDto = kwhItsWebClient.post()
                .uri((UriBuilder uriBuilder) -> uriBuilder.path("/meta/alarms")
                        .build())
                .body(BodyInserters.fromMultipartData("payload", payload))
                .retrieve()
                .bodyToMono(AtWorkAlarmResponseDto.class)
                .block();
        log.debug("create alarm atWorkAlarmResponseDto: " + atWorkAlarmResponseDto);

        // add the token and alarmDto1 to request body
        /*AlarmResponseDto alarmResponseDto = webClient.post()
                .uri(path)
                .headers(h -> h.setBearerAuth(jwt.getTokenValue()))
                .bodyValue(alarmDto)
                .retrieve()
                .bodyToMono(AlarmResponseDto.class)
                .block();
        log.debug("alarmResponseDto: " + alarmResponseDto);
        return alarmResponseDto;*/
        return atWorkAlarmResponseDto;
    }

    @Override
    public void webhookAlarm(AtWorkAlarmResponseDto atWorkAlarmResponseDto) {
        // log the response
        log.debug("webhookAlarm in service atWorkAlarmResponseDto: " + atWorkAlarmResponseDto);
    }
}
