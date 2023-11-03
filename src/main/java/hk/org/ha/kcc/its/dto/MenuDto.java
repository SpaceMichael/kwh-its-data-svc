package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

  private int id; // e.g 1? or use Prefixed?

  private String title; // Bed Cleansing

  private String description; // e.g "Request form" , "Request form, Tracker" etc

  private String remarks;

  private String icon; // e. g
                       // "https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"

  private String url; // e.g
                      // "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest

  private Boolean activeFlag;
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
