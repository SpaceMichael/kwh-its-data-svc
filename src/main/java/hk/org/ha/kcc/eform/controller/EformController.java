package hk.org.ha.kcc.eform.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hk.org.ha.kcc.eform.dto.EformResponseDto;
import hk.org.ha.kcc.eform.service.EformService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "sample", description = "Sample API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(EformController.BASE_URL)
public class EformController {

    public static final String BASE_URL = "/api/v1/eform";

    private final EformService eformService;

    @GetMapping
    public EformResponseDto getEformList() {
        return eformService.getEformList();
    }
}
