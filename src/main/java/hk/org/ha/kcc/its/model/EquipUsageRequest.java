package hk.org.ha.kcc.its.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Audited
@Table(name = "equip_usage_request")
public class EquipUsageRequest extends Auditable {

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
    private String SerNo;// E09-123567890123
    @Column(name = "model", length = 50)
    private String Model; // ELITDESK PU 8000G5 SFF
    @Column(name = "belong_to", length = 50)
    private String BelongTo;// KWH Ward 8A
    @Column(name = "type", length = 50)
    private String Type; // MED
    @Column(name = "case_no", length = 50)
    private String CaseNo; // e.g. HN123454677 patient strap
    @Column(name = "patient_name", length = 100)
    private String PatientName; // KC
    @Column(name = "active_flag")
    private Boolean activeFlag;

    /*
    private String hospitalCode; // e.g KWH
    private String dept; // e.g. M&G
    private String ward; // e.g. 12BMN
    private String cubicle; // e.g. 1
    private Integer eamNo; // 1824496
    private String  SerNo;// E09-123567890123
    private String  Model; //ELITDESK PU 8000G5 SFF
    private String  BelongTo;// KWH Ward 8A
    private String Type; //MED
    private String CaseNo; //e.g. HN123454677
    private String PatientName; // KC
    Date; //e.g. 2023-12-31 => reference createdDate from Auditable
    Time; //from frontend e.g. 10:00 =>reference createdDate from Auditable
    User; //get from token => reference createdBy from Auditable
     */
}
