package hk.org.ha.kcc.its.model;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// @Audited
@Table(name = "service")
public class Services extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // e.g 1? or use Prefixed?
    @Column(name = "service_name", length = 50)
    private String serviceName; //
    @Column(name = "active_flag")
    private Boolean activeFlag;
/*    @Column(name = "alarm_code",  length = 50)
    private String alarmCode; // Houseman  = service name ?
    @Column(name = "alarm_location_code",  length = 50)
    private String alarmLocationCode; // IT // from equipment location e.g 3A need add alarm location code
    @Column(name = "alarm_severity",  length = 50)
    private String alarmSeverity; // normal or critical?
    @Column(name = "alarm_type",  length = 50)
    private String alarmType; // Test alarm type 2 or hard code?
    @Column(name = "alarm_title",  length = 50)
    private String alarmTitle; // Test alarm title 2 or hard code?
    @Column(name = "alarm_message",  length = 50)
    private String alarmMessage; // Test alarm message 2 or hard code?
    @Column(name = "alarm_ack_threshold")
    private Integer alarmAckThreshold; // 1 or hard code?
    @Column(name = "alarm_webhook")
    private Boolean alarmWebhook; // true
    @Column(name = "alarm_notification_required")
    private Boolean alarmNotificationRequired; // true*/


}
