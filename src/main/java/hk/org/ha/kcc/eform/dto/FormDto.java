package hk.org.ha.kcc.eform.dto;

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
    private String url;
    private BarcodeDto barcode;
}
