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
// @Audited
@Table(name = "service")
public class Services extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // e.g 1? or use Prefixed?
    @Column(name = "service_code", length = 50)
    private String serviceCode; // PAED_HOUSEMAN_T1,  PAED_HOUSEMAN_RESP_NEUR
    @Column(name = "service_name", length = 50)
    private String serviceName; // Houseman (Team 1), Houseman (Respi + Neuro)
    @Column(name = "alarm_type", length = 50)
    private String alarmType; // Houseman (Team 1), Houseman (Respi + Neuro)




}
