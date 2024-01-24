package hk.org.ha.kcc.its.model;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
@Table(name = " service_alarm_sender")
public class ServiceAlarmSender extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // e.g 1
    @Column(name = "service_code", length = 50)
    private String serviceCode; // Houseman  = service name ?
    @Column(name = "location_code", length = 50)
    private String locationCode; //  e.g 3A
    @Column(name = "sender_id")
    private String senderId; //  override the sender group "1" or "1,2,3"

}
