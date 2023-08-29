package hk.org.ha.kcc.eform.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.eform.dto.StorageListRespDto;
import hk.org.ha.kcc.eform.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.lang.invoke.MethodHandles;

@Tag(name = "storage", description = "storage API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(StorageController.BASE_URL)
public class StorageController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/bitrix/users";  // /bitrix/users/:corpId/workgroups for dummy test

    private final StorageService storageService;

    @Operation(summary = "Get storage list")
    @GetMapping(value = "/{corpId}/workgroups/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StorageListRespDto getStorageList(@PathVariable String corpId) throws Exception {
        log.debug("Get Storage List corpId." + corpId);
        return storageService.getStorageList();
    }

}
