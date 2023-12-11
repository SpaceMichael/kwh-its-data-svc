package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
    private Boolean activeFlag;
    private String date; // date from user input
    private String time; // time from user input

    /*
    @Id
    @Column(length = 12)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
    @GenericGenerator(name = "booking_generator",
            strategy = "hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator",
            parameters = {@Parameter(name = "sequence_name", value = "booking_seq")
                    , @Parameter(name = "prefix", value = "EU-")})
    private String id;
    @Column(name = "eam_no")
    private Integer eamNo; // 1824496
    @Column(name = "ser_no", length = 50)
    private String  SerNo;// E09-123567890123
    @Column(name = "model", length = 50)
    private String  Model; // ELITDESK PU 8000G5 SFF
    @Column(name = "belong_to", length = 50)
    private String  BelongTo;// KWH Ward 8A
    @Column(name = "type", length = 50)
    private String Type; // MED
    @Column(name = "case_no", length = 50)
    private String CaseNo; // e.g. HN123454677 patient strap
    @Column(name = "patient_name", length = 100)
    private String PatientName; // KC
      @Column(name = "active_flag")
    private Boolean activeFlag;
     */
}
