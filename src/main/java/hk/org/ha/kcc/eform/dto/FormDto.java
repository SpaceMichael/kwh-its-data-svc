package hk.org.ha.kcc.eform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDto {
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    private String url;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BarcodeDto barcode;
}
