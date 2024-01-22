package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAlarmSenderDto {
    private Integer id; // e.g 1
    private String serviceCode; // Houseman  = service name ?
    private String locationCode; //  e.g 3A
    private Integer senderId; // override the sender group if

}
