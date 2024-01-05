package hk.org.ha.kcc.its.dto.alarm;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmUserDto {
    private Integer id;
    private String corpId;
    private String name;
    private LocalDateTime time;
}
