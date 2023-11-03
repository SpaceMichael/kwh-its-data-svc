package hk.org.ha.kcc.its.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
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
@Table(name = "bed_cleansing_request")
public class BedCleansingRequest extends Auditable {

  @Id
  @Column(length = 12)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
  @GenericGenerator(name = "booking_generator",
      strategy = "hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator",
      parameters = {@Parameter(name = "sequence_name", value = "booking_seq"),
          @Parameter(name = "prefix", value = "BC-")})
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  // @Column(name = "id")
  private String id;


  @Column(name = "hospital_code", length = 3)
  private String hospitalCode; // e.g KWH

  @Column(name = "dept_code", length = 10)
  private String dept; // e.g M&G

  @Column(name = "ward_code", length = 10)
  private String ward; // e.g 12BMN

  @Column(name = "cubicle_no", length = 1)
  private String cubicle; // e.g 1

  @Column(name = "bed_checked")
  private Boolean bedChecked; // e.g all clean in the room 全房大抹

  @Column(name = "bed_no", length = 5)
  private String bedNo; // e.g 01 or 1-02

  @Column(name = "bed_type", length = 20)
  private String bedType; // e.g 普通床 掛牌床

  @Column(name = "cleaning_process", length = 50)
  private String cleaningProcess; // e.g 清潔工序 ->床,床簾,環境

  @Column(name = "detergent", length = 50)
  private String detergent; // e.g 清潔劑(detergent)->漂白水

  @Column(name = "status", length = 20)
  private String status; // apply or pending or completed

  @Column(name = "requestor", length = 50)
  private String requestor; // e,g cmw002 // use token check?

  @Column(name = "requestor_name", length = Integer.MAX_VALUE)
  private String requestorName; // e,g chan siu man

  @Column(name = "requestor_contact_no", length = 50)
  private String requestorContactNo;

  @Column(name = "cleaner", length = 50)
  private String Cleaner; // e.g Mobile Device 008?

  @Column(name = "active_flag")
  private Boolean activeFlag; //

  @Lob
  @Column(name = "remarks", length = Integer.MAX_VALUE)
  private String remarks;
}
