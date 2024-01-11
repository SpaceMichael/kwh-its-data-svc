package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAckReceiverDto {
    private Integer id; // e.g 1
    private String serviceCode; // Houseman  = service name ?
    private String locationCode; //  e.g 3A
    private Integer escalationId; // from sam3 =>ack_escalation_id

}
