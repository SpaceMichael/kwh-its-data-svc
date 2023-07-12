package hk.org.ha.kcc.eforms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EformResponseDto {
    private Boolean success;
    private DataDto data;
}