package hk.org.ha.kcc.eforms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hk.org.ha.kcc.eforms.dto.BarcodeDto;
import hk.org.ha.kcc.eforms.dto.DataDto;
import hk.org.ha.kcc.eforms.dto.EformsResponseDto;
import hk.org.ha.kcc.eforms.dto.FormDto;

@Service
public class EformServiceImpl implements EformService{

    @Override
    public EformsResponseDto getEformList() {
        EformsResponseDto eformResponseDto = new EformsResponseDto();
        eformResponseDto.setSuccess(true);
        List<FormDto> forms = new ArrayList<>();
        forms.add(FormDto.builder().title("Bed Cleansing").url("http://kwhwebc.home/").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
        forms.add(FormDto.builder().title("Drug dispensing Tracker").url("http://kwhwebc.home/").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
        forms.add(FormDto.builder().title("Lab Report Tracker").url("http://kwhwebc.home/").barcode(BarcodeDto.builder().enable(true).key("patientId").build()).build());
        eformResponseDto.setData(DataDto.builder().forms(forms).build());
        return eformResponseDto;
    }
    
}
