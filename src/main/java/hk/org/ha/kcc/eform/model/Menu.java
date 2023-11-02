package hk.org.ha.kcc.eform.model;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menu")
public class Menu extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // e.g 1? or use Prefixed?

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;  // Bed Cleansing

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;   // e.g "Request form" , "Request form, Tracker" etc

    @Column(name = "remarks", length = Integer.MAX_VALUE)
    private String remarks;

    @Column(name = "icon", length = Integer.MAX_VALUE)
    private String icon; // e. g  "https://kwh-its-eform-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"

    @Column(name = "url", length = Integer.MAX_VALUE)
    private String url;   // e.g "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest

    @Column(name = "active_flag")
    private Boolean activeFlag;
}
