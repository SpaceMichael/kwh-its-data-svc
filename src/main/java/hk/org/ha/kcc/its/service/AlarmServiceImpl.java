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
import java.util.List;


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
                "\"severity\":\"null\",\n" + // default normal
                "\"webhook\":\"true\",\n" +  //
                "\"senderGroupIds\": [" + alarmDto.getSenderGroupIds() + "],\n" +// add the alarmDto.get
                "\"notifySenderRst\":\"true\",\n" +
                "\"notifySenderNoResp\":\"true\",\n" +
                "\"title\": \"" + alarmDto.getTitle() + "\"\n" +
                "}";
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

        return atWorkAlarmResponseDto;
    }

    @Override
    public void webhookAlarm(AtWorkAlarmResponseDto atWorkAlarmResponseDto) {
        String alarmId = atWorkAlarmResponseDto.getData().getId();
        //log.debug("alarmId: " + alarmId);
        if (alarmId != null) {
            //get the server request
            List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll().stream()
                    .filter(serviceRequest -> serviceRequest.getAlarmId() != null && serviceRequest.getAlarmId().equals(alarmId)).toList();

            ServiceRequest serviceRequest = serviceRequestList.stream().findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("service request record is null, please check " + alarmId));
            // if the service request is not null, update the service request
            if (serviceRequest != null) {
                serviceRequest.setAck_by(atWorkAlarmResponseDto.getData().getFirstResponse().getUser().getCorpId());
                serviceRequest.setAck_date(atWorkAlarmResponseDto.getData().getFirstResponse().getUser().getTime());
                serviceRequest.setModifiedBy("system");
                serviceRequestRepository.save(serviceRequest);
            } else {
                log.debug("service request record is null, please check " + atWorkAlarmResponseDto.getData().getId());
                throw new ResourceNotFoundException("service request record is null, please check " + atWorkAlarmResponseDto.getData().getId());
            }
        }
    }
}
