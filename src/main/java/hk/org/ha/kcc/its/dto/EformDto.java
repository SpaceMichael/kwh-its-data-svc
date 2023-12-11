package hk.org.ha.kcc.its.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EformDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id; // e.g 1? or use Prefixed?
    private String title; // Bed Cleansing
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description; // e.g "Request form" , "Request form, Tracker" etc
    private String remarks;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon; // e.g // "https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"
    private String url; // e.g // "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest
    private Boolean activeFlag;
    private Boolean enable;
    private String barcodeKey;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url2; // e.g service url for cleaner use
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title2; // e.g "Bed Cleansing status" may be not use
    private String qrcodeType; // e.g "BED"
}

/*
 * private int id; // e.g 1? or use Prefixed? private String title; // Bed Cleansing private String
 * description; // e.g "Request form" , "Request form, Tracker" etc private String remarks; private
 * String icon; // e. g
 * "https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png" private
 * String url; // e.g
 * "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest private
 * Boolean activeFlag;
 */
