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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon; // e.g // "https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"
    private String url; // e.g // "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest
    private Boolean enable;
    private String qrcodeType; // e.g "BED"
}
