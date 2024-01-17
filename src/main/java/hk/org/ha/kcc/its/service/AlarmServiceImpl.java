package hk.org.ha.kcc.its.service;


import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;

import hk.org.ha.kcc.its.dto.alarm.AlarmDto;
import hk.org.ha.kcc.its.dto.alarm.AtWorkAlarmResponseDto;
import hk.org.ha.kcc.its.model.ServiceRequest;
import hk.org.ha.kcc.its.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.lang.invoke.MethodHandles;


@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    private final WebClient kwhItsWebClient;

    private final ServiceRequestRepository serviceRequestRepository;

    public AlarmServiceImpl(WebClient kwhItsWebClient, ServiceRequestRepository serviceRequestRepository) {
        this.kwhItsWebClient = kwhItsWebClient;
        this.serviceRequestRepository = serviceRequestRepository;
    }


    @Override
    public AtWorkAlarmResponseDto create(AlarmDto alarmDto) {

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
        /*String id = String.valueOf(atWorkAlarmResponseDto.getData().getId());
        log.debug("acknowledge alarm id : {}", id);
        log.debug("acknowledge by: " + atWorkAlarmResponseDto.getData().getFirstResponse().getUser().getCorpId());
        log.debug("acknowledge DateTime : " + atWorkAlarmResponseDto.getData().getFirstResponse().getUser().getTime());*/

        if (atWorkAlarmResponseDto.getData().getId() != null) {
            //get the server request
            ServiceRequest serviceRequest = serviceRequestRepository.findAll().stream().filter(sr -> sr.getAlarmId().equals(atWorkAlarmResponseDto.getData().getId())).findFirst().orElse(null);
            // if the service request is not null, update the service request
            if (serviceRequest != null) {
                serviceRequest.setAck_by(atWorkAlarmResponseDto.getData().getFirstResponse().getUser().getCorpId());
                serviceRequest.setAck_date(atWorkAlarmResponseDto.getData().getFirstResponse().getUser().getTime());
                serviceRequestRepository.save(serviceRequest);
            } else {
                log.debug("service request record is null, please check " + atWorkAlarmResponseDto.getData().getId());
                throw new ResourceNotFoundException("service request record is null, please check " + atWorkAlarmResponseDto.getData().getId());
            }
        }
    }
}
