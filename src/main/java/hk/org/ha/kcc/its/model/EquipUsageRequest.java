package hk.org.ha.kcc.its.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator;
import jakarta.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// @Audited
@Table(name = "equip_usage_request")
public class EquipUsageRequest extends Auditable {

  @Id
  @Column(length = 12)
  @GeneratedValue(generator = "equip_usage_request_generator")
  @GenericGenerator(name = "equip_usage_request_generator",
      strategy = "hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator",
      parameters = {
          @Parameter(name = PrefixedSequenceIdGenerator.SEQUENCE_PARAM,
              value = "equip_usage_request_seq"),
          @Parameter(name = PrefixedSequenceIdGenerator.PREFIX_PARAM, value = "EU-")})
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
  @Column(name = "date", length = 50)
  private String date; // date from user input 2023/12/11 YYYY/MM/DD
  @Column(name = "time", length = 50)
  private String time; // time from user input 01:00 - 23:59


}
