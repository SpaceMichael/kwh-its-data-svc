package hk.org.ha.kcc.its.dto.alarm;

import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtWorkAlarmResponseDto {
    private Boolean success;
    private AlarmErrorDto error;
    private AlarmDataDto data;
}
