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
}
