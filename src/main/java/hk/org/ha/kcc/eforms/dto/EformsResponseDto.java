package hk.org.ha.kcc.eforms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EformsResponseDto {
    private Boolean success;
    private DataDto data;
}
