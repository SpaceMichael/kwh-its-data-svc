package hk.org.ha.kcc.its.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import hk.org.ha.kcc.its.dto.DataDto;
import hk.org.ha.kcc.its.dto.DetailDataDto;
import hk.org.ha.kcc.its.dto.DetailDto;
import hk.org.ha.kcc.its.dto.EformResponseDto;
import hk.org.ha.kcc.its.dto.FormDto;

@Service
public class EformServiceImpl implements EformService {

  @Override
  public ResponseEntity<EformResponseDto> getEformList(String qrcode) {
    String serverAddress = "https://kwh-its-data-svc-kccclinical-dev.cldkwhtst1.server.ha.org.hk/";
    String iconBedCleansing = serverAddress + "/iconBedCleansing.png";
    String iconDrugDispensing = serverAddress + "/iconDrugDispensing.png";
    String iconLabReport = serverAddress + "/iconLabReport.png";

    // qrcode not null and not empty
    if (qrcode != null && !qrcode.isEmpty()) {
      EformResponseDto eformResponseDto = new EformResponseDto();
      eformResponseDto.setSuccess(true);
      List<FormDto> forms = new ArrayList<>();
      // forms.add(FormDto.builder().title("Bed
      // Cleansing").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest").build());
      forms.add(FormDto.builder().title("Bed Cleansing").description("Request form").url(
          "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest")
          .icon(iconBedCleansing).build());
      // remove barcode in the forms


      DetailDto details = new DetailDto();
      details.setType("bed");
      // details.setData(DetailDataDto.builder().dept("M&G").ward("16A1").bedNo("10").build());
      details.setData(DetailDataDto.builder().ward("12BM").cubicle("1").bedNo("1-02")
          .bedChecked(false).build());
      eformResponseDto.setData(DataDto.builder().forms(forms).details(details).build());
      // return eformResponseDto;
      return ResponseEntity.ok(eformResponseDto);
    } else {
      EformResponseDto eformResponseDto = new EformResponseDto();
      eformResponseDto.setSuccess(true);
      List<FormDto> forms = new ArrayList<>();
      forms.add(FormDto.builder().title("Bed Cleansing").description("Request form")
          .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansing")
          .icon(iconBedCleansing).build());
      forms.add(FormDto.builder().title("Drug dispensing Tracker")
          .description("Request form, Tracker")
          .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/DrugDispensing")
          .icon(iconDrugDispensing).build());
      forms.add(
          FormDto.builder().title("Lab Report Tracker").description("Request form, Report search")
              .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/LabReport")
              .icon(iconLabReport).build());
      eformResponseDto.setData(DataDto.builder().forms(forms).build());
      // return eformResponseDto;
      // retrun eformResponseDto with png file imageBytes
      return ResponseEntity.ok().body(eformResponseDto);
      /*
       * return ResponseEntity.ok() .contentType(MediaType.IMAGE_PNG).body(eformResponseDto);
       */
    }
  }
}
