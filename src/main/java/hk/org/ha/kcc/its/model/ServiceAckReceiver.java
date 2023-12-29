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
//@Audited
@Table(name = "service_ack_receiver")
public class ServiceAckReceiver extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // e.g 1
    @Column(name = "service_code", length = 50)
    private String serviceCode; // Houseman  = service name ?
    @Column(name = "location_code", length = 50)
    private String locationCode; //  e.g 3A
    @Column(name = "escalation_id")
    private Integer escalationId; // from sam3 =>ack_escalation_id
    @Column(name = "active_flag")
    private Boolean activeFlag;
/*    @Column(name = "ack_threshold")
    private Integer ackThreshold;
    @Column(name = "webhook")
    private Boolean webhook;
    @Column(name = "ack_timeout")
    private Integer ackTimeout;
    @Column(name = "notification_required")
    private Boolean notificationRequired;*/
    /*
    ackThreshold;
    webhook;
    ackTimeout;
    notificationRequired;

    alarmDto.setAckThreshold(1);
    alarmDto.setWebhook(true);
    alarmDto.setAckTimeout(1);
    alarmDto.setNotificationRequired(true);
    */
}
