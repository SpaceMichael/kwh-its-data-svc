package hk.org.ha.kcc.its.model;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Audited
@Table(name = "eam")
public class Eam extends Auditable {

    @Id
    @Column(name = "eam_no", length = 12)
    private int eamNo; // 1824496  ~1800222
    @Column(name = "ser_no", length = 50)
    private String serNo;// E09-123567890123  5CD0522TB4
    @Column(name = "model", length = 50)
    private String model; // ELITDESK PU 8000G5 SFF PROBPPK 430 G7
    @Column(name = "belong_to", length = 50)
    private String belongTo;// KWH Ward 8A   LOC? KWH-SB-01-122?
    @Column(name = "type", length = 50)
    private String type; // MED ?
    @Column(name = "case_no", length = 50)
    private String caseNo; // e.g. HN123454677 patient strap
    @Column(name = "active_flag")
    private Boolean activeFlag;
}
