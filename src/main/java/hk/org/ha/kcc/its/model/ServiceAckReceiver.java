package hk.org.ha.kcc.its.model;

import lombok.*;

import jakarta.persistence.*;


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

}
