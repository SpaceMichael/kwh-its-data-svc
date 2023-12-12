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

    @Value("${app.its.api.path}")
    private String serverAddress;

    @Value("${app.its.alarm.path}")
    private String alarmPath;
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    //private JwtTokenUtil jwtTokenUtil;

    @Override
    public AlarmResponseDto create(AlarmDto alarmDto) {

        String path = alarmPath + "api/v1/alarms";
        WebClient webClient = WebClient.create(alarmPath + "api/v1/alarms");
        // use get method
        // get the current user token
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("jwt: " + jwt);

        AlarmDto alarmDto1 = new AlarmDto();
        alarmDto1.setRequestId("SR-2300002"); // get the service-request Id
        alarmDto1.setAlarmCode("Testing");    // set the alarm code set in Alarm code ward 12XX?
        alarmDto1.setLocationCode("IT");      // location
        alarmDto1.setSeverity("normal");      //
        alarmDto1.setType("Test alarm type 2");
        alarmDto1.setTitle("Test alarm title 2");
        alarmDto1.setMessage("Test alarm message 2");
        alarmDto1.setAckThreshold(1);
        alarmDto1.setWebhook(true);
        alarmDto1.setNotificationRequired(true);
        // add the token and alarmDto1 to request body
        AlarmResponseDto alarmResponseDto = webClient.post()
                .uri(path)
                .headers(h -> h.setBearerAuth(jwt.getTokenValue()))
                .bodyValue(alarmDto1)
                .retrieve()
                .bodyToMono(AlarmResponseDto.class)
                .block();
        System.out.println("alarmResponseDto: " + alarmResponseDto);
        return alarmResponseDto;
    }

    @Override
    public AlarmResponseDto createAlram(String id, // SR Id
                                        String alarmCode, // get from SR in DB
                                        String locationCode, // get from SR in DB e.g 3A
                                        String severity,  // get from SR in DB
                                        String type,// get from SR in DB
                                        String title,// get from SR in DB
                                        String message,// get from SR in DB
                                        Integer ackThreshold,// get from SR in DB
                                        Boolean webhook,// get from SR in DB
                                        Boolean notificationRequired// get from SR in DB
    ) {
        return null;
    }
}
