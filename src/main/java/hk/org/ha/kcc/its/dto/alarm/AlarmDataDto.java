package hk.org.ha.kcc.its.dto.alarm;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDataDto {
    private String id;
    private Integer escalationId;
    private String severity;
    private String title;
    private String body;
    private Integer currentLevel;
    private Integer maxLevel;
    private Boolean isEnded;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime nextProcessTime;

    private AlarmFirstResponseDto firstResponse;
}
