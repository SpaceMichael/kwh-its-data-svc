package hk.org.ha.kcc.its.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
@Table(name = "bed_cleansing_request")
public class BedCleansingRequest extends Auditable {

  @Id
  @Column(length = 12)
  @GeneratedValue(generator = "bed_cleansing_request_generator")
  @GenericGenerator(name = "bed_cleansing_request_generator",
      strategy = "hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator",
      parameters = {
          @Parameter(name = PrefixedSequenceIdGenerator.SEQUENCE_PARAM,
              value = "bed_cleansing_request_seq"),
          @Parameter(name = PrefixedSequenceIdGenerator.PREFIX_PARAM, value = "BC-")})
  private String id;
  @Column(name = "hospital_code", length = 3)
  private String hospitalCode; // e.g KWH
  @Column(name = "dept_code", length = 10)
  private String dept; // e.g M&G
  @Column(name = "ward_code", length = 10)
  private String ward; // e.g 12BMN
  @Column(name = "cubicle_no", length = 10)
  private String cubicle; // e.g 1
  @Column(name = "whole_bed_cleansing")
  private Boolean wholeBed; // e.g all clean in the room 全房大抹
  @Column(name = "bed_no", length = 10)
  private String bedNo; // e.g 01 or 1-02
  @Column(name = "bed_type", length = 20)
  private String bedType; // e.g 普通床 or 掛牌床 bed? listed bed?
  @Column(name = "cleaning_process", length = 50)
  private String cleaningProcess; // e.g 清潔工序 ->床,床簾,環境 bed, curtain, env
  @Column(name = "detergent", length = 50)
  private String detergent; // e.g 清潔劑(detergent)->漂白水, tricel = real name : detergent,tricel
  @Column(name = "status", length = 20)
  private String status; // apply or pending or completed
  @Column(name = "requestor_id")
  private String requestor;
  @Column(name = "requestor_contact_no")
  private Integer requestorContactNo;
  @Column(name = "cleaner", length = 50)
  private String cleaner; // e.g Mobile Device 008?

  @Lob
  @Column(name = "remarks")
  private String remarks;
  @Column(name = "eform_id", length = 20, insertable = false, updatable = false)
  private Integer eformId;

  @NotAudited
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "eform_id")
  private Eform eform;

  public void assignEform(Eform eform) {
    this.eform = eform;
    this.eformId = eform.getId();
  }

  public void removeEform(Eform eform) {
    this.eform = null;
    this.eformId = null;
  }
}
