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

}

