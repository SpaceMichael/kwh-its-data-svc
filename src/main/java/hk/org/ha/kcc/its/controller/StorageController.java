package hk.org.ha.kcc.its.controller;

import java.lang.invoke.MethodHandles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.StorageListRespDto;
import hk.org.ha.kcc.its.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "storage", description = "storage API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(StorageController.BASE_URL)
public class StorageController {
  private static final AlsXLogger log =
      AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
  public static final String BASE_URL = "/bitrix/users"; // /bitrix/users/:corpId/workgroups for
                                                         // dummy test

  @Autowired
  private StorageService storageService;

  @Operation(summary = "Get storage list")
  @GetMapping(value = "/{corpId}/workgroups/", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public StorageListRespDto getStorageList(@PathVariable String corpId) {
    log.debug("Get Storage List corpId." + corpId);
    return storageService.getStorageList();
  }
}
