package hk.org.ha.kcc.eform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailDataDto {
    private String dept;
    private String ward;
    private String bedNo;

}
