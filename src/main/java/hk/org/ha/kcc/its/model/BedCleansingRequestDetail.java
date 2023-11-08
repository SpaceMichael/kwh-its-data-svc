package hk.org.ha.kcc.its.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
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
@Table(name = "bed_cleansing_detail")
public class BedCleansingRequestDetail extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "bed_cleansing_request_id")
  private Integer bedCleansingRequestId; // case_no
  @Column(name = "status")
  private String status; // e.g pending
  /*
   * @Column(name = "requester_id") private String requesterId; // e.g Chan Siu Man cms002 crop_id?
   * may be store on bed_cleaning_request table
   */

  @Column(name = "service_id")
  private String serviceId; // use id to mapping request_item: bed cleansing?
  @Column(name = "active_flag")
  private Boolean activeFlag; // for Deactive?
  @Column(name = "cleaner", length = 50)
  private String Cleaner; // e.g Device 008?
  @Lob
  @Column(name = "remarks", length = Integer.MAX_VALUE)
  private String remarks;
}
