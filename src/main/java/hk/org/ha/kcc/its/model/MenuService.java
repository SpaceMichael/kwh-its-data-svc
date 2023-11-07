package hk.org.ha.kcc.its.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
//@Audited
@Table(name = "menu_service")
public class MenuService extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id; // e.g 1? or use Prefixed?

  @Column(name = "title", length = Integer.MAX_VALUE)
  private String title; // Bed Cleansing

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description; // e.g "Request form" , "Request form, Tracker" etc

  @Column(name = "remarks", length = Integer.MAX_VALUE)
  private String remarks;

  @Column(name = "icon", length = Integer.MAX_VALUE)
  private String icon; // e. g
                       // "https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"

  @Column(name = "url", length = Integer.MAX_VALUE)
  private String url; // e.g
                      // "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest

  @Column(name = "active_flag")
  private Boolean activeFlag;

  @Column(name = "enable")
  private Boolean enable;

  @Column(name = "barcode_key") // cannot use key keyword in mssql
  private String barcodeKey;
}
