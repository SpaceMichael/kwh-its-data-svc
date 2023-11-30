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
    private String formId; //
    private Boolean activeFlag;

    /*
    private String corpId; // ttk799
    private Integer formId; // form Id is eform id e.g:1
    private Boolean activeFlag;
     */
}
