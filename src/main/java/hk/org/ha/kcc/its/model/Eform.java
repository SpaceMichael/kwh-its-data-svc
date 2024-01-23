package hk.org.ha.kcc.its.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "eform")
public class Eform extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // e.g 1? or use Prefixed?
    @Column(name = "title", length = 100)
    private String title; // Bed Cleansing
    @Column(name = "description", length = 100)
    private String description; // e.g "Request form" , "Request form, Tracker" etc
    @Column(name = "icon", length = 100)
    private String icon; // e.g "https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"
    @Column(name = "url", length = 200)
    private String url; // e.g request url for requester use "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest
    @Column(name = "enable")  // under barcode Dto use
    private Boolean enable;
    @Column(name = "qrcode_type", length = 50)
    private String qrcodeType; // e.g BED , SR on the QRcode

}
