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
        forms.add(FormDto.builder().title("title1").url("url1").barcode(BarcodeDto.builder().enable(true).key("key1").build()).build());
        forms.add(FormDto.builder().title("title2").url("url2").barcode(BarcodeDto.builder().enable(true).key("key2").build()).build());
        forms.add(FormDto.builder().title("title3").url("url3").barcode(BarcodeDto.builder().enable(true).key("key3").build()).build());
        eformResponseDto.setData(DataDto.builder().forms(forms).build());
        return eformResponseDto;
    }
    
}
