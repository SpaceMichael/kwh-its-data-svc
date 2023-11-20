package hk.org.ha.kcc.its.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Audited
@Table(name = "eform")
public class Eform extends Auditable {

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
    private String icon; // e.g "https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"
    @Column(name = "url", length = Integer.MAX_VALUE)
    private String url; // e.g request url for requester use "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest
    @Column(name = "active_flag")
    private Boolean activeFlag;
    @Column(name = "enable")  // under barcode Dto use
    private Boolean enable;
    @Column(name = "barcode_key") // cannot use key keyword in mssql
    private String barcodeKey;
    @Column(name = "url2", length = Integer.MAX_VALUE)
    private String url2; // e.g service url for cleaner use
    @Column(name = "title2", length = Integer.MAX_VALUE)
    private String title2; // e.g "Bed Cleansing status" may be not use

   /* @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BedCleansingRequest> bedCleansingRequestList = new ArrayList<>();

    public void addBedCleansingRequest(BedCleansingRequest bedCleansingRequest) {
        bedCleansingRequestList.add(bedCleansingRequest);
        bedCleansingRequest.setEform(this);
    }

    public void removeBedCleansingRequest(BedCleansingRequest bedCleansingRequest) {
        bedCleansingRequestList.remove(bedCleansingRequest);
        bedCleansingRequest.setEform(null);
    }*/
}
