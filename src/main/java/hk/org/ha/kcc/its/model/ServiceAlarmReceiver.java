package hk.org.ha.kcc.its.model;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Audited
@Table(name = "service_alarm_receiver")
public class ServiceAlarmReceiver extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // e.g 1
    @Column(name = "service_code", length = 50)
    private String serviceCode; // PAED_HOUSEMAN_T1
    @Column(name = "start_time", length = 10)
    private LocalTime startTime; //   8:00
    @Column(name = "end_time", length = 10)
    private LocalTime endTime; //    17:00
    @Column(name = "ack_timeout")
    private Integer ackTimeout; //  1,2,3
    @Column(name = "escalation_id")
    private Integer escalationId; // from sam3 = to_escalation_id
    @Column(name = "alarm_title")
    private String alarmTitle;  // HOUSEMAN CALL at Ward {0}
    @Lob
    @Column(name = "alarm_message")
    private String alarmMessage; // Houseman Call at Ward {0}
    @Column(name = "start_time_sat", length = 10)
    private LocalTime startTimeSat; //   8:00 ?
    @Column(name = "end_time_sat", length = 10)
    private LocalTime endTimeSat; //    17:00
    @Column(name = "start_time_sun", length = 10)
    private LocalTime startTimeSun; //   8:00 ?
    @Column(name = "end_time_sun", length = 10)
    private LocalTime endTimeSun; //    17:00


}
