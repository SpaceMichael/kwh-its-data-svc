package hk.org.ha.kcc.eform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hk.org.ha.kcc.eform.dto.BarcodeDto;
import hk.org.ha.kcc.eform.dto.DataDto;
import hk.org.ha.kcc.eform.dto.EformResponseDto;
import hk.org.ha.kcc.eform.dto.FormDto;

@Service
public class EformServiceImpl implements EformService{

    @Override
    public EformResponseDto getEformList() {
        EformResponseDto eformResponseDto = new EformResponseDto();
        eformResponseDto.setSuccess(true);
        List<FormDto> forms = new ArrayList<>();
        forms.add(FormDto.builder().title("Bed Cleansing").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansing").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
        forms.add(FormDto.builder().title("Drug dispensing Tracker").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/DrugDispensing").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
        forms.add(FormDto.builder().title("Lab Report Tracker").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/LabReport").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());

        eformResponseDto.setData(DataDto.builder().forms(forms).build());
        return eformResponseDto;
    }
    
}
