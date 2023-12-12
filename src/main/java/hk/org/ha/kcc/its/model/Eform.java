package hk.org.ha.kcc.its.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
@Table(name = "eform")
public class Eform extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // e.g 1? or use Prefixed?
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
    @Column(name = "qrcode_type", length = 50)
    private String qrcodeType; // e.g BED , SR on the QRcode


    //@Column(name = "alarm_code",  length = 50)
    //private String alarmCode; // Houseman  = get from  service name ,
    /*@Column(name = "alarm_location_code",  length = 50)
    private String alarmLocationCode; // IT*/ // from equipment location e.g 3A need add alarm location code
/*    @Column(name = "alarm_severity",  length = 50)
    private String alarmSeverity; // normal or critical?
    @Column(name = "alarm_type",  length = 50)
    private String alarmType; // Test alarm type 2 or hard code?
    @Column(name = "alarm_title",  length = 50)
    private String alarmTitle; // Test alarm title 2 or hard code?
    @Column(name = "alarm_message",  length = 50)
    private String alarmMessage; // Test alarm message 2 or hard code?
    @Column(name = "alarm_ack_threshold")
    private Integer alarmAckThreshold; // 1 or hard code?
    @Column(name = "alarm_webhook")
    private Boolean alarmWebhook; // true
    @Column(name = "alarm_notification_required")
    private Boolean alarmNotificationRequired; // true*/


    /*

         alarmDto1.setRequestId("SR-2300002"); // get the service-request Id
        alarmDto1.setAlarmCode("Testing");    // set the alarm code set in Alarm code ward 12XX?
        alarmDto1.setLocationCode("IT");      // location
        alarmDto1.setSeverity("normal");      //
        alarmDto1.setType("Test alarm type 2");
        alarmDto1.setTitle("Test alarm title 2");
        alarmDto1.setMessage("Test alarm message 2");
        alarmDto1.setAckThreshold(1);
        alarmDto1.setWebhook(true);
        alarmDto1.setNotificationRequired(true);
     */

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "eform", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BedCleansingRequest> bedCleansingRequestList = new ArrayList<>();

    public void addBedCleansingRequest(BedCleansingRequest bedCleansingRequest) {
        bedCleansingRequestList.add(bedCleansingRequest);
        bedCleansingRequest.setEform(this);
    }

    public void removeBedCleansingRequest(BedCleansingRequest bedCleansingRequest) {
        bedCleansingRequestList.remove(bedCleansingRequest);
        bedCleansingRequest.setEform(null);
    }
}
