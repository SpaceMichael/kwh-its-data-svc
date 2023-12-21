package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.dto.AlarmResponseDto;
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
        // use get method
        // get the current user token
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AlarmDto alarmDto1 = new AlarmDto();
        alarmDto1.setRequestId("SR-230008"); // get the service-request Id
        alarmDto1.setAckEscalationId(73);
        alarmDto1.setToEscalationId(73);
        alarmDto1.setSeverity("normal");
        alarmDto1.setType("HOUSEMAN TYPE");
        alarmDto1.setTitle("HOUSEMAN TITLE");
        alarmDto1.setMessage("HOUSEMAN MESSAGE");
        alarmDto1.setAckThreshold(1);
        alarmDto1.setWebhook(true);
        alarmDto1.setAckTimeout(1);
        alarmDto1.setNotificationRequired(true);

        /*
          "requestId": "SR-20007",
          "ackEscalationId": 73,
          "toEscalationId": 73,
          "severity": "normal",
          "type": "ALARM TYPE",
          "title": "ALARM TITLE ",
          "message": "ALARM  MESSAGE",
          "ackThreshold": 1,
          "webhook": true,
          "ackTimeout": 1,
          "notificationRequired": true
         */
        // add the token and alarmDto1 to request body
        AlarmResponseDto alarmResponseDto = webClient.post()
                .uri(path)
                .headers(h -> h.setBearerAuth(jwt.getTokenValue()))
                .bodyValue(alarmDto1)
                .retrieve()
                .bodyToMono(AlarmResponseDto.class)
                .block();
        log.debug("alarmResponseDto: " + alarmResponseDto);
        return alarmResponseDto;
    }

}
