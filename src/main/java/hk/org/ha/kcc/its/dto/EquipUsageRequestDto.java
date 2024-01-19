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
public class EquipUsageRequestDto {

    private String id;
    private Integer eamNo; // 1824496
    private String SerNo;// E09-123567890123
    private String Model; // ELITDESK PU 8000G5 SFF
    private String BelongTo;// KWH Ward 8A
    private String Type; // MED
    private String CaseNo; // e.g. HN123454677 patient strap
    private String PatientName; // KC
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private String date; // date from user input
    private String time; // time from user input
    private LocalDateTime userDateTime; // time from user input
    private Integer total; // total number of records. group by eamNo

}
