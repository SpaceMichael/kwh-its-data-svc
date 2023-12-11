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
    private Long id; // e.g 1? or use Prefixed?
    @Column(name = "service_name", length = 50)
    private String serviceName; //
    @Column(name = "active_flag")
    private Boolean activeFlag;
    /*
    service_name
     */


}
