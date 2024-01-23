package hk.org.ha.kcc.its.model;

import lombok.*;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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


}
