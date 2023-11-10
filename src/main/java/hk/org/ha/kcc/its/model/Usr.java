package hk.org.ha.kcc.its.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Audited
@Table(name = "usr")
public class Usr extends Auditable {
    @Id
    @Column(name = "corp_id", length = 20)
    private String corpId;

    @Column(length = 50)
    private String name;

    @Column(length = 20)
    private String role;

    @Column(name = "active_flag")
    private Boolean active_Flag;
}
