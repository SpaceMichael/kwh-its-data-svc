package hk.org.ha.kcc.its.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.*;
import hk.org.ha.kcc.its.mapper.EformMapper;
import hk.org.ha.kcc.its.model.Eform;
import hk.org.ha.kcc.its.repository.EformRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;

@Service
public class EformServiceImpl implements EformService {

    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    @Value("${app.its.api.path}")
    private String serverAddress;

    @Value("${app.its.eform.path}")
    private String serverFrontEndAddress;

    private final EformRepository eformRepository;

    private final EformMapper eformMapper;

    private final UserAccessService userAccessService;

    public EformServiceImpl(EformRepository eformRepository, EformMapper eformMapper,
                            UserAccessService userAccessService) {
        this.eformRepository = eformRepository;
        this.eformMapper = eformMapper;
        this.userAccessService = userAccessService;
    }

    @Override
    public ResponseEntity<EformResponseDto> getEformList(String qrcode, String currentAuditor) {
        // get user access by corpId && Split the userAccessDto.getFormId() by '," to list of formID
        UserAccessDto userAccessDto = this.userAccessService.getDtoById(currentAuditor);


        // for test
        List<Integer> formIdList = new ArrayList<>();
        if (userAccessDto.getFormId() != null && !userAccessDto.getFormId().isEmpty()) {
            String[] formIdArray = userAccessDto.getFormId().split(",");
            for (String formId : formIdArray) {
                formIdList.add(Integer.parseInt(formId));
            }
        }
        // qrcode not null and not empty
        if (qrcode != null && !qrcode.isEmpty()) {
            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            List<FormDto> forms = new ArrayList<>();
            // get menuService by title= Bed Cleansing id =1 for hardcode test
            // get the value of qrcode eg = "BED|KWH|12BG|9|7-02" for test
            // split it "|" to detail field TYPE|WARD|CUBICLE|BEDNO
            DetailDto details = new DetailDto();
            String[] qrcodeArray;

            // check the qrcode contain "|" exist or not, if not,throw the exception error
            if (qrcode.contains("|")) {
                qrcodeArray = qrcode.split("\\|");
            } else {
                log.debug("qrcode NOT CORRECT " + qrcode);
                // throw new IllegalArgumentException("qrcode NOT CORRECT" + qrcode);
                throw new ResourceNotFoundException("qrcode NOT CORRECT" + qrcode);
            }
            // Integer IdFromQrCode = findByQrcodeType(qrcodeArray[0]);
            List<EformDto> eformDtoList = this.findByQrcodeType(qrcodeArray[0]);

            // check eformDtoList contain UserAcess formID List
            if (eformDtoList != null && !eformDtoList.isEmpty()) {
                for (EformDto eformDto : eformDtoList) {
                    if (formIdList.contains(eformDto.getId())) {
                        forms.add(
                                FormDto.builder().title(eformDto.getTitle()).description(eformDto.getDescription())
                                        .url(serverFrontEndAddress + eformDto.getUrl())
                                        .icon(serverAddress + eformDto.getIcon()).build());
                    }
                }
            }

            details.setType(qrcodeArray[0]);
            details.setData(DetailDataDto.builder().ward(qrcodeArray[2]).cubicle(qrcodeArray[3])
                    .bedNo(qrcodeArray[4]).build());

            eformResponseDto.setData(DataDto.builder().forms(forms).details(details).build());
            return ResponseEntity.ok(eformResponseDto);
        } else {

            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            // get user access by corpId
            // Split the userAccessDto.getFormId() by '," to list of formID

            List<FormDto> forms = new ArrayList<>();
            // get all the menu and fill the forms field and filter userAccessDto.form_id()

            List<EformDto> eformDtoList =
                    this.getAllDto().stream().filter(eformDto -> formIdList.contains((eformDto.getId())))
                            .toList();
            for (EformDto eformDto : eformDtoList) {
                forms.add(FormDto.builder().title(eformDto.getTitle())
                        .description(eformDto.getDescription()).url(serverFrontEndAddress + eformDto.getUrl())
                        .icon(serverAddress + eformDto.getIcon()).build());
                // how to set the barcode object? enable ? and key? no qrcode set enable is false?
                // or no key?
            }
            eformResponseDto.setData(DataDto.builder().forms(forms).build());
            return ResponseEntity.ok().body(eformResponseDto);
        }
    }

    @Override
    public EformDto create(EformDto eformDto) {
        Eform eform = this.eformMapper.EformDtoToEform(eformDto);
        eform.setEnable(Optional.ofNullable(eformDto.getEnable()).orElse(true));
        return this.eformMapper.EformToEformDto(this.eformRepository.save(eform));
    }

    @Override
    public List<EformDto> getAllDto() {
        List<Eform> eformList = this.eformRepository.findAll();
        return eformList.stream().map(eformMapper::EformToEformDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public EformDto getDtoById(Integer id) {
        Eform eform = this.eformRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Eform not found"));
        return this.eformMapper.EformToEformDto(eform);
    }

    @Override
    public EformDto updateById(Integer id, EformDto eformDto) {
        Eform eform = this.eformRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Eform not found"));
        BeanUtils.copyProperties(eformDto, eform, getNullPropertyNames(eformDto));
        return this.eformMapper.EformToEformDto(this.eformRepository.save(eform));
    }

    @Override
    public void deleteById(Integer id) {
        this.eformRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Eform not found"));
        this.eformRepository.deleteById(id);
    }

    @Override
    public List<EformDto> findByQrcodeType(String qrcodeType) {

        List<EformDto> matchingEform = this.eformRepository.findAll().stream()
                .filter(eform -> eform.getQrcodeType().equals(qrcodeType))
                .map(eformMapper::EformToEformDto).collect(Collectors.toList());
        // if matching Eform is empty, throw the exception ResourceNotFoundException
        if (!matchingEform.isEmpty()) {
            return matchingEform;
        } else {
            throw new ResourceNotFoundException("Eform not found");
        }
    }
}
