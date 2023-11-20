package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccessDto {

    private String corpId; // ttk799
    private Integer formId; // form Id is eform id
    private Boolean activeFlag;

    /*
     @Id
    @Column(name = "corp_id", length = 50)
    private String corpId; // ttk799

    @Column(name = "form_id")
    private Integer formId; // form Id is eform id

    @Column(name = "active_flag") // 1 & 0
    private Boolean activeFlag;
     */
}
