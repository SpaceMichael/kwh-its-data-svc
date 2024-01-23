package hk.org.ha.kcc.its.dto.alarm;

import lombok.*;

//@Schema(name = "Alarm")
@Data
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {

    private String alarmType;  // not null  type=>alarmType get from service_alarm_receiver alarm_type , defined by self?
    private Integer escalationId; // not null toEscalationId = escalationId get from service_alarm_receiver escalation_id
    private String message; // not null, get form service_alarm_receiver alarm_message
    private String title;  // get from service_alarm_receiver alarm_title

    private String severity; // default normal
    private Integer ackThreshold; //1 predefine in escalation flow
    private Boolean webhook; // predefine on option in escalation flow
    private String senderGroupIds; // predefine in escalation flow
    private Boolean notifySenderRst; // predefine in escalation flow
    private Boolean notifySenderNoResp; // predefine in escalation flow


}

