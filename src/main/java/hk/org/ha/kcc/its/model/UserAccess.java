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
@Table(name = "user_access")
public class UserAccess extends Auditable {

    @Id
    @Column(name = "corp_id", length = 50)
    private String corpId; // ttk799

    @Column(name = "form_id")
    private String formId; // formId is array contain eform.id e.g  1, 2, 3

    @Column(name = "active_flag") // 1 & 0
    private Boolean activeFlag;
    /*
    corp_id, form_id
     */
}
