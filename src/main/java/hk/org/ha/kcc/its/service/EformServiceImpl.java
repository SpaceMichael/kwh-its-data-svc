package hk.org.ha.kcc.its.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.*;
import hk.org.ha.kcc.its.mapper.EformMapper;
import hk.org.ha.kcc.its.model.Eform;
import hk.org.ha.kcc.its.repository.EformRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EformServiceImpl implements EformService {

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());


    private final EformRepository eformRepository;

    private final EformMapper eformMapper;

    private final BedCleansingRequestService bedCleansingRequestService;

    public EformServiceImpl(EformRepository eformRepository, EformMapper eformMapper, BedCleansingRequestService bedCleansingRequestService) {
        this.eformRepository = eformRepository;
        this.eformMapper = eformMapper;
        this.bedCleansingRequestService = bedCleansingRequestService;
    }

    @Value("${server.path}")
    private String serverAddress;

    @Value("${server.env}")
    private String serverEnv;

    @Override
    public ResponseEntity<EformResponseDto> getEformList(String qrcode) {
       /*   log.debug("serverAddress: " + serverAddress);
            log.debug("serverEnv: " + serverEnv);*/

        // qrcode not null and not empty
        if (qrcode != null && !qrcode.isEmpty()) {
            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            List<FormDto> forms = new ArrayList<>();
            // get menuService by title= Bed Cleansing id =1 for hardcode test
            /* forms.add(FormDto.builder().title("Bed Cleansing")
                    .description("Request form")
                    .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest")
                    .icon(iconBedCleansing).build());
             */
            // get the value of qrcode eg = "BED|KWH|12BG|7|7-02" for test
            // splite it "|" to  detail field TYPE|WARD|CUBICLE|BEDNO
            DetailDto details = new DetailDto();
            String[] qrcodeArray;

            // check the qrcode contain "|" exist or not, if not,throw the exception error
            if (qrcode.contains("|")) {
                qrcodeArray = qrcode.split("\\|");
            } else {
                log.debug("qrcode NOT CORRECT " + qrcode);
                //throw new IllegalArgumentException("qrcode NOT CORRECT" + qrcode);
                throw new ResourceNotFoundException("qrcode NOT CORRECT" + qrcode);
            }


            // EformDto eformDto = this.menuService.getDtoById(1);  ---> read the qr code type value from qr code
            EformDto eformDto = this.getDtoById(findByQrcodeType(qrcodeArray[0]));
            // check qrcode get the qrcode type ="BED" , = eform id = 1? or seacrh it from db and return the eformdto
            // use the menuServiceDto to fill the forms field with url
            forms.add(FormDto.builder().title(eformDto.getTitle())
                    .description(eformDto.getDescription())
                    .url(eformDto.getUrl())
                    .icon(serverAddress + eformDto.getIcon()).build());

            details.setType(qrcodeArray[0]);
            details.setData(DetailDataDto.builder()
                    .ward(qrcodeArray[2])
                    .cubicle(qrcodeArray[3])
                    .bedNo(qrcodeArray[4])
                    .build());

            eformResponseDto.setData(DataDto.builder().forms(forms).details(details).build());
            return ResponseEntity.ok(eformResponseDto);
        } else {
            /*log.debug("serverAddress: " + serverAddress);
            log.debug("serverEnv: " + serverEnv);*/
            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            List<FormDto> forms = new ArrayList<>();
            // get all the menu and fill the forms field
            List<EformDto> eformDtoList = this.getAllDto();
            for (EformDto eformDto : eformDtoList) {
                forms.add(FormDto.builder().title(eformDto.getTitle())
                        .description(eformDto.getDescription())
                        .url(eformDto.getUrl())
                        .icon(serverAddress + eformDto.getIcon()).build());
                //log.debug("Icon: " + serverAddress + menuDto.getIcon());
            }
            eformResponseDto.setData(DataDto.builder().forms(forms).build());
            return ResponseEntity.ok().body(eformResponseDto);
        }
    }

    @Override
    public EformDto create(EformDto eformDto) {
        Eform eform = this.eformMapper.EformDtoToEform(eformDto);
        if (eformDto.getActiveFlag() == null) {
            eform.setActiveFlag(true);
        }
        if (eformDto.getEnable() == null) {
            eform.setEnable(true);
        }
        return this.eformMapper.EformToEformDto(this.eformRepository.save(eform));
    }

    @Override
    public List<EformDto> getAllDto() {
        // filter the active flag is true
        List<Eform> eformList = this.eformRepository.findAll();
        return eformList.stream().map(eformMapper::EformToEformDto).filter(EformDto::getActiveFlag)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public EformDto getDtoById(Integer id) {
        Eform eform = this.eformRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Eform not found"));
        return this.eformMapper.EformToEformDto(eform);
    }

    @Override
    public EformDto updateById(Integer id, EformDto eformDto) {
        Eform eform = this.eformRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Eform not found"));
        if (eformDto.getTitle() != null) {
            eform.setTitle(eformDto.getTitle());
        }
        if (eformDto.getDescription() != null) {
            eform.setDescription(eformDto.getDescription());
        }
        if (eformDto.getRemarks() != null) {
            eform.setRemarks(eformDto.getRemarks());
        }
        if (eformDto.getIcon() != null) {
            eform.setIcon(eformDto.getIcon());
        }
        if (eformDto.getUrl() != null) {
            eform.setUrl(eformDto.getUrl());
        }
        if (eformDto.getActiveFlag() != null) {
            eform.setActiveFlag(eformDto.getActiveFlag());
        }
        if (eformDto.getEnable() != null) {
            eform.setEnable(eformDto.getEnable());
        }
        if (eformDto.getBarcodeKey() != null) {
            eform.setBarcodeKey(eformDto.getBarcodeKey());
        }
        if (eformDto.getUrl2() != null) {
            eform.setUrl2(eformDto.getUrl2());
        }
        if (eformDto.getTitle2() != null) {
            eform.setTitle2(eformDto.getTitle2());
        }
        if (eformDto.getQrcodeType() != null) {
            eform.setQrcodeType(eformDto.getQrcodeType());
        }
        return this.eformMapper.EformToEformDto(this.eformRepository.save(eform));
    }

    @Override
    public void deleteById(Integer id) {
        try {
            this.eformRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Eform not found");
        }
    }

    @Override
    public Integer findByQrcodeType(String qrcodeType) {

        Eform matchingEform = this.eformRepository.findAll()
                .stream()
                .filter(eform -> eform.getQrcodeType().equals(qrcodeType))
                .filter(Eform::getActiveFlag)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Eform not found"));

        // if matchingEform is null, throw the exception ResourceNotFoundException
        if (matchingEform != null) {
            return matchingEform.getId();
        } else {
            throw new ResourceNotFoundException("Eform not found");
        }
    }
}
