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
  /*  @Column(name = "severity", length = 20)
    private String severity; // normal
    @Column(name = "type", length = 20)
    private String type; // test alarm
    @Column(name = "title", length = 20)
    private String title;
    @Column(name = "message", length = 50)
    private String message;
    @Column(name = "ack_threshold")
    private Integer ackThreshold; //1
    @Column(name = "webhook")
    private Boolean webhook; // true
    @Column(name = "ack_timeout")
    private Integer ackTimeout; // 1
    @Column(name = "notification_required")
    private Boolean notificationRequired; // true
*/

    /*
    id
    service_code
    location_code
    escalation_id

    private String toCorpId;// no need use this time for future use
    private String severity; // normal
    private String type; // test alarm
    private String title;
    private String message;
    private Integer ackThreshold; //1
    private Boolean webhook; // true
    private Integer ackTimeout; // 1
    private Boolean notificationRequired; // true

    */
}
