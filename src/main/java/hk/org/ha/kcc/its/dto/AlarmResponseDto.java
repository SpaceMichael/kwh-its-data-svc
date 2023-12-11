package hk.org.ha.kcc.its.dto;

import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponseDto {
    private Boolean success;
    private Long id;
    private String error;
}
