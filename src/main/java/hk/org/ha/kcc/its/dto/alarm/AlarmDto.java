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
    private String escalationId; // not null toEscalationId = escalationId get from service_alarm_receiver escalation_id
    private String message; // not null, get form service_alarm_receiver alarm_message
    private String title;  // get from service_alarm_receiver alarm_title

    private String severity; // default normal
    private Integer ackThreshold; //1 predefine in escalation flow
    private Boolean webhook; // predefine on option in escalation flow
    private String senderGroupIds; // predefine in escalation flow
    private Boolean notifySenderRst; // predefine in escalation flow
    private Boolean notifySenderNoResp; // predefine in escalation flow

    /*
    "message": "1224",
    "escalationId": "73",
    "alarmType": "Houseman",
    "ackThreshold":1,
    "severity":"normal",
    "title": "test title" ,
    "webhook": true,          // pre-defined in escalation flow
    "senderGroupIds": [73],   // pre-defined value in escalation flow
    "notifySenderRst": true,  // pre-defined value in escalation flow
    "notifySenderNoResp": true // predefined value in escalation flow
     */

}

