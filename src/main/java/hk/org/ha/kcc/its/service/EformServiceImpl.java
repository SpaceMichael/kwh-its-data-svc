package hk.org.ha.kcc.its.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EformServiceImpl implements EformService {

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    private final MenuService menuService;

    private final BedCleansingRequestService bedCleansingRequestService;

    public EformServiceImpl(MenuService menuService, BedCleansingRequestService bedCleansingRequestService) {
        this.menuService = menuService;
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
            // forms.add(FormDto.builder().title("Bed
            // Cleansing").url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest").build());

            // get menuService by title= Bed Cleansing id =1 for hardcode test
            MenuDto menuDto = this.menuService.getDtoById(1);
            // use the menuServiceDto to fill the forms field with url
            forms.add(FormDto.builder().title(menuDto.getTitle())
                    .description(menuDto.getDescription())
                    .url(menuDto.getUrl2())
                    .icon(serverAddress + menuDto.getIcon()).build());

            /*forms.add(FormDto.builder().title("Bed Cleansing")
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
                log.debug("qrcode no splite " + qrcode);
                throw new IllegalArgumentException("qrcode no splite " + qrcode);
            }

            details.setType(qrcodeArray[0]);
            details.setData(DetailDataDto.builder()
                    .ward(qrcodeArray[2])
                    .cubicle(qrcodeArray[3])
                    .bedNo(qrcodeArray[4])
                    .build());
            /*details.setType("bed");
            // details.setData(DetailDataDto.builder().dept("M&G").ward("16A1").bedNo("10").build());
            details.setData(DetailDataDto.builder().ward("12BM").cubicle("1").bedNo("1-02")
                    .bedChecked(false).build());*/
            eformResponseDto.setData(DataDto.builder().forms(forms).details(details).build());
            return ResponseEntity.ok(eformResponseDto);
        } else {
            /*log.debug("serverAddress: " + serverAddress);
            log.debug("serverEnv: " + serverEnv);*/
            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            List<FormDto> forms = new ArrayList<>();
            // get all the menu and fill the forms field
            List<MenuDto> menuDtoList = this.menuService.getAllDto();
            for (MenuDto menuDto : menuDtoList) {
                forms.add(FormDto.builder().title(menuDto.getTitle())
                        .description(menuDto.getDescription())
                        .url(menuDto.getUrl())
                        .icon(serverAddress + menuDto.getIcon()).build());
                //log.debug("Icon: " + serverAddress + menuDto.getIcon());
            }
            eformResponseDto.setData(DataDto.builder().forms(forms).build());
            return ResponseEntity.ok().body(eformResponseDto);
        }
    }
}
