package hk.org.ha.kcc.eform.service;

import java.util.ArrayList;
import java.util.List;

import hk.org.ha.kcc.eform.dto.*;
import org.springframework.stereotype.Service;

@Service
public class EformServiceImpl implements EformService {

    @Override
    public EformResponseDto getEformList(String qrcode) {
        String serverAddress = "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk";
        String iconBedCleansing = "/static/iconBedCleansing.png";
        String iconDrugDispensing = "/static/iconDrugDispensing.png";
        String iconLabReport = "/static/iconLabReport.png";

        // qrcode not null and not empty
        if (qrcode != null && !qrcode.isEmpty()) {
            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            List<FormDto> forms = new ArrayList<>();
            forms.add(FormDto.builder().title("Bed Cleansing").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest").build());
            // remove barcode in the forms


            DetailDto details = new DetailDto();
            details.setType("bed");
            //details.setData(DetailDataDto.builder().dept("M&G").ward("16A1").bedNo("10").build());
            details.setData(DetailDataDto.builder().ward("12BM").cubicle("1").bedNo("1-01").bedChecked(false).build());
            eformResponseDto.setData(DataDto.builder().forms(forms).details(details).build());
            return eformResponseDto;
        } else {
            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            List<FormDto> forms = new ArrayList<>();
            //forms.add(FormDto.builder().title("Bed Cleansing").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansing").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
            forms.add(FormDto.builder().title("Bed Cleansing").description("Request form").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansing").icon(serverAddress+iconBedCleansing).build());
            //forms.add(FormDto.builder().title("Drug dispensing Tracker").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/DrugDispensing").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
            forms.add(FormDto.builder().title("Drug dispensing Tracker").description("Request form, Tracker").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/DrugDispensing").icon(serverAddress+iconDrugDispensing).build());
            //forms.add(FormDto.builder().title("Lab Report Tracker").description("Request form, Report search").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/LabReport").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
            forms.add(FormDto.builder().title("Lab Report Tracker").description("Request form, Report search").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/LabReport").icon(serverAddress+iconLabReport).build());
            eformResponseDto.setData(DataDto.builder().forms(forms).build());
            return eformResponseDto;
        }
    }

}
