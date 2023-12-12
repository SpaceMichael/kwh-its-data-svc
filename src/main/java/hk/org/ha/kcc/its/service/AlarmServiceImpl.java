package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.AlarmDto;
import hk.org.ha.kcc.its.dto.AlarmResponseDto;
import hk.org.ha.kcc.its.mapper.AlarmMapper;
import hk.org.ha.kcc.its.model.Alarm;
import hk.org.ha.kcc.its.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {

    @Value("${app.its.api.path}")
    private String serverAddress;

    @Value("${app.its.alarm.path}")
    private String alarmPath;
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    private final AlarmRepository alarmRepository;

    private final AlarmMapper alarmMapper;

    public AlarmServiceImpl(AlarmRepository alarmRepository, AlarmMapper alarmMapper) {
        this.alarmRepository = alarmRepository;
        this.alarmMapper = alarmMapper;
    }

    @Override
    public AlarmResponseDto create(AlarmDto alarmDto) {

        String path = alarmPath + "api/v1/alarms";
        WebClient webClient = WebClient.create(alarmPath + "api/v1/alarms");
        // use get method
        // get the current user token
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("jwt: " + jwt);

       /* AlarmDto alarmDto1 = new AlarmDto();
        alarmDto1.setRequestId("SR-2300002"); // get the service-request Id
        alarmDto1.setAlarmCode("Testing");
        alarmDto1.setLocationCode("IT");
        alarmDto1.setSeverity("normal");
        alarmDto1.setType("Test alarm type 2");
        alarmDto1.setTitle("Test alarm title 2");
        alarmDto1.setMessage("Test alarm message 2");
        alarmDto1.setAckThreshold(1);
        alarmDto1.setWebhook(true);
        alarmDto1.setNotificationRequired(true);*/
        // add the token and alarmDto1 to request body
        AlarmResponseDto alarmResponseDto = webClient.post()
                .uri(path)
                .headers(h -> h.setBearerAuth(jwt.getTokenValue()))
                .bodyValue(alarmDto)
                .retrieve()
                .bodyToMono(AlarmResponseDto.class)
                .block();
        //System.out.println("alarmResponseDto: " + alarmResponseDto);
        log.debug("alarmResponseDto: " + alarmResponseDto);
        return alarmResponseDto;
    }

    @Override
    public AlarmDto getDtoBySRId(Integer id, String location) {
        List<Alarm> alarmList = alarmRepository.findAll().stream()
                .filter(alarm -> alarm.getServiceId().equals(id))
                .filter(alarm -> alarm.getLocationCode().equals(location))
                .collect(Collectors.toList());
        AlarmDto alarmDto = alarmList.stream().findFirst().map(alarmMapper::AlarmToAlarmDto).orElseThrow(() -> new ResourceNotFoundException("Alarm not found"));
        return alarmDto;
    }

    @Override
    public AlarmDto createDto(AlarmDto alarmDto) {
        // alarmDto -> alarm
        Alarm alarm = alarmMapper.AlarmDtoToAlarm(alarmDto);
        if (alarmDto.getActiveFlag() != null) {
            alarm.setActiveFlag(alarmDto.getActiveFlag());
        }
        // save and return
        return alarmMapper.AlarmToAlarmDto(alarmRepository.save(alarm));
    }

    @Override
    public List<AlarmDto> getAllDto() {
        return alarmRepository.findAll().stream().map(alarmMapper::AlarmToAlarmDto).collect(Collectors.toList());
    }

    @Override
    public AlarmDto updateDtoById(Integer id, AlarmDto alarmDto) {
        Alarm alarm = alarmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Alarm not found"));
        //set alarm value if  alarmDto value not null
        if (alarmDto.getAlarmCode() != null) {
            alarm.setAlarmCode(alarmDto.getAlarmCode());
        }
        if (alarmDto.getLocationCode() != null) {
            alarm.setLocationCode(alarmDto.getLocationCode());
        }
        if (alarmDto.getSeverity() != null) {
            alarm.setSeverity(alarmDto.getSeverity());
        }
        if (alarmDto.getType() != null) {
            alarm.setType(alarmDto.getType());
        }
        if (alarmDto.getTitle() != null) {
            alarm.setTitle(alarmDto.getTitle());
        }
        if (alarmDto.getMessage() != null) {
            alarm.setMessage(alarmDto.getMessage());
        }
        if (alarmDto.getEscalationId() != null) {
            alarm.setEscalationId(alarmDto.getEscalationId());
        }
        if (alarmDto.getAckThreshold() != null) {
            alarm.setAckThreshold(alarmDto.getAckThreshold());
        }
        if (alarmDto.getWebhook() != null) {
            alarm.setWebhook(alarmDto.getWebhook());
        }
        if (alarmDto.getAckTimeout() != null) {
            alarm.setAckTimeout(alarmDto.getAckTimeout());
        }
        if (alarmDto.getNotificationRequired() != null) {
            alarm.setNotificationRequired(alarmDto.getNotificationRequired());
        }
        if (alarmDto.getServiceId() != null) {
            alarm.setServiceId(alarmDto.getServiceId());
        }
        if (alarmDto.getActiveFlag() != null) {
            alarm.setActiveFlag(alarmDto.getActiveFlag());
        }
        /*
        private String alarmCode;
        private String locationCode;
        private String severity;
        private String type;
        private String title;
        private String message;
        private Integer escalationId;
        private Integer ackThreshold;
        private Boolean webhook;
        private Integer ackTimeout;
        private Boolean notificationRequired;
        private Integer serviceId;
        private Boolean activeFlag;
         */
        // save and return
        return alarmMapper.AlarmToAlarmDto(alarmRepository.save(alarm));
    }


}
