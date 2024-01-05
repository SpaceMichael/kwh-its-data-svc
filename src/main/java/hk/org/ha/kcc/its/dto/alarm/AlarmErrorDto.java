package hk.org.ha.kcc.its.dto.alarm;

import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmErrorDto {
    private String code;
    private String message;
}
