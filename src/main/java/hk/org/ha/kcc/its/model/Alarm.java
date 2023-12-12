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
@Table(name = "alarm")
public class Alarm extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // e.g 1
    @Column(name = "alarm_code", length = 50)
    private String alarmCode; // Houseman  = service name ?
    @Column(name = "location_code", length = 50)
    private String locationCode; // IT // from equipment location e.g 3A need add alarm location code
    @Column(name = "severity", length = 50)
    private String severity; // normal or critical?
    @Column(name = "type", length = 50)
    private String type; // Test alarm type 2 or hard code?
    @Column(name = "title", length = 50)
    private String title; // Test alarm title 2 or hard code?
    @Column(name = "escalationId", length = 50)
    private Integer escalationId;
    @Column(name = "message", length = 50)
    private String alarmMessage; // Test alarm message 2 or hard code?
    @Column(name = "ack_threshold")
    private Integer ackThreshold; // 1 or hard code?
    @Column(name = "webhook")
    private Boolean webhook; // true
    @Column(name = "ack_timeout")
    private Integer ackTimeout; // 1
    @Column(name = "notification_required")
    private Boolean notificationRequired; // true*/
}
