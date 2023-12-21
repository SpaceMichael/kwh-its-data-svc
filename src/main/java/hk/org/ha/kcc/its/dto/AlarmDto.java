package hk.org.ha.kcc.its.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

//@Schema(name = "Alarm")
@Data
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {
    private String requestId;// ->SR ID
    private Integer ackEscalationId;
    private Integer toEscalationId;
    private String toCorpId;// no need use this time for future use
    private String severity; // normal
    private String type; // test alarm
    private String title;
    private String message;
    private Integer ackThreshold; //1
    private Boolean webhook; // true
    private Integer ackTimeout; // 1
    private Boolean notificationRequired; // true
    /*
      "requestId": "string",
      "ackEscalationId": 0,
      "toEscalationId": 0,
      "toCorpId": "string",
      "severity": "string",
      "type": "string",
      "title": "string",
      "message": "string",
      "ackThreshold": 0,
      "webhook": true,
      "ackTimeout": 0,
      "notificationRequired": true
     */

    /*
    backup 20231218
    private String requestId;// ->SR ID
    private String alarmCode; // Testing
    private String locationCode; //3A
    private String severity; // normal
    private String type; // test alarm
    private String title;
    private String message;
    private Integer escalationId; // null
    private Integer ackThreshold; //1
    private Boolean webhook; // true
    private Integer ackTimeout; // 1
    private Boolean notificationRequired; // true
    private Integer serviceId; // my use
    private Boolean activeFlag;// my use
     */
}

