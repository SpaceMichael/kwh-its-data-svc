package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;

@Data
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAlarmReceiverDto {
    private Integer id; // e.g 1
    private String serviceCode; // PAED_HOUSEMAN_T1
    private LocalTime startTime; //   8:00
    private LocalTime endTime; //    17:00
    private Integer ackTimeout; //  1,2,3
    private Integer escalationId; //
    private String alarmTitle;
    private String alarmMessage;
    private LocalTime startTimeSat; //
    private LocalTime endTimeSat; //
    private LocalTime startTimeSun; //
    private LocalTime endTimeSun; //

}
