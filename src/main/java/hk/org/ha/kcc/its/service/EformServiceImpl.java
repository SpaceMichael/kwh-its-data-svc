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

    @Override
    public ResponseEntity<EformResponseDto> getEformList(String qrcode) {
        //System.out.println("serverAddress: " + serverAddress);
        /*String serverAddress = "https://kwh-its-data-svc-kccclinical-dev.cldkwhtst1.server.ha.org.hk";
        String iconBedCleansing = serverAddress + "/iconBedCleansing.png";
        String iconDrugDispensing = serverAddress + "/iconDrugDispensing.png";
        String iconLabReport = serverAddress + "/iconLabReport.png";*/

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
                    .url(menuDto.getUrl())
                    //.icon(menuDto.getIcon()).build());
                    .icon(serverAddress + menuDto.getIcon()).build());

            /*forms.add(FormDto.builder().title("Bed Cleansing")
                    .description("Request form")
                    .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest")
                    .icon(iconBedCleansing).build());
             */
            // get bed cleansing request id =BC-2300033 for hardcode test - > if the QRcode is true value, get the detail from QRcode info
            BedCleansingRequestDto bedCleansingRequestDto = bedCleansingRequestService.getDtoById("BC-2300033");
            DetailDto details = new DetailDto();
            // and fill the details field - > if the QRcode is true value, get the detail from QRcode info
            details.setType(bedCleansingRequestDto.getBedType());
            details.setData(DetailDataDto.builder()
                    .ward(bedCleansingRequestDto.getWard())
                    .cubicle(bedCleansingRequestDto.getCubicle())
                    .bedNo(bedCleansingRequestDto.getBedNo())
                    .bedChecked(bedCleansingRequestDto.getWholeBed())
                    .build());
            /*details.setType("bed");
            // details.setData(DetailDataDto.builder().dept("M&G").ward("16A1").bedNo("10").build());
            details.setData(DetailDataDto.builder().ward("12BM").cubicle("1").bedNo("1-02")
                    .bedChecked(false).build());*/
            eformResponseDto.setData(DataDto.builder().forms(forms).details(details).build());
            // return eformResponseDto;
            return ResponseEntity.ok(eformResponseDto);
        } else {
            EformResponseDto eformResponseDto = new EformResponseDto();
            eformResponseDto.setSuccess(true);
            List<FormDto> forms = new ArrayList<>();
            // get all the menu and fill the forms field
            List<MenuDto> menuDtoList = this.menuService.getAllDto();
            for (MenuDto menuDto : menuDtoList) {
                forms.add(FormDto.builder().title(menuDto.getTitle())
                        .description(menuDto.getDescription())
                        .url(menuDto.getUrl())
                        //.icon(serverAddress+menuDto.getIcon()).build());
                        .icon(menuDto.getIcon()).build());
                //System.out.println("menuDto.getIcon(): " + menuDto.getIcon());
                log.debug("menuDto.getIcon(): " + menuDto.getIcon());

            }

      /*      forms.add(FormDto.builder()
                    .title("Bed Cleansing")
                    .description("Request form")
                    .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansing")
                    .icon(iconBedCleansing).build());
            forms.add(FormDto.builder().title("Drug dispensing Tracker")
                    .description("Request form, Tracker")
                    .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/DrugDispensing")
                    .icon(iconDrugDispensing).build());
            forms.add(FormDto.builder()
                    .title("Lab Report Tracker")
                    .description("Request form, Report search")
                    .url("https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/LabReport")
                    .icon(iconLabReport).build());*/
            eformResponseDto.setData(DataDto.builder().forms(forms).build());
            return ResponseEntity.ok().body(eformResponseDto);
        }
    }
}
