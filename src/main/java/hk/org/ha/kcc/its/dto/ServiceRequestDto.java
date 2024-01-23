package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDto {
    private String id;
    private String caseNo; // e.g. HN123454677 patient strap
    private String location; // e.g. KWH
    private String cubicleNo; // e.g. 1
    private String bedNo; // e.g. 1
    private Integer serviceId;  // e.g. 1
    private String remarks; // e.g. 1
    private String alarmId;
    private Boolean success;
    private Integer escalationId;
    private String errorMessage;
    private String ack_by;
    private LocalDateTime ack_date;


}


