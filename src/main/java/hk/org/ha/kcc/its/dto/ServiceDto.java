package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private Integer id; // e.g 1? or use Prefixed?
    private String serviceCode; //
    private String serviceName; //
    private String alarmType;

}
