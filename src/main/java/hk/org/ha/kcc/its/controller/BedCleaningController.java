package hk.org.ha.kcc.its.controller;

import java.lang.invoke.MethodHandles;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.service.BedCleansingRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "bed-cleaning", description = "bed-cleaning API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(BedCleaningController.BASE_URL)
public class BedCleaningController {

  private static final AlsXLogger log =
      AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
  public static final String BASE_URL = "/api/v1/bed-cleansing/request";

  private final BedCleansingRequestService bedCleansingRequestService;

  private final AuditorAware<String> auditorAware;


  public BedCleaningController(BedCleansingRequestService bedCleansingRequestService,
      AuditorAware<String> auditorAware) {
    this.bedCleansingRequestService = bedCleansingRequestService;
    this.auditorAware = auditorAware;
  }

  // post BedCleansing
  @Operation(summary = "Create new BedCleansingRequest")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BedCleansingRequestDto createBCRequest(
      @RequestBody BedCleansingRequestDto bedCleansingRequestDto) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("create by: " + currentAuditor);
    return this.bedCleansingRequestService.create(bedCleansingRequestDto);
  }

  // get all
  @Operation(summary = "Get list of BedCleansingRequest")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Iterable<BedCleansingRequestDto> getAllBCRequest() {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("get all by: " + currentAuditor);
    return this.bedCleansingRequestService.getAllDto();
  }

  // get by id
  @Operation(summary = "Get the BedCleansingRequest by id")
  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public BedCleansingRequestDto getBCRequestById(@PathVariable String id) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("get by id: " + id + "by: " + currentAuditor);
    return this.bedCleansingRequestService.getDtoById(id);
  }

  // patch by id
  @Operation(summary = "Update the BedCleansingRequest by id")
  @PatchMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public BedCleansingRequestDto updateBCRequestById(@PathVariable String id,
      @RequestBody BedCleansingRequestDto bedCleansingRequestDto) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("update by id: " + id + "by: " + currentAuditor);
    return this.bedCleansingRequestService.update(id, bedCleansingRequestDto);
  }

  // delete by id
  @Operation(summary = "Delete the BedCleansingRequest by id")
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteBCRequestById(@PathVariable String id) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("delete by id: " + id + "by: " + currentAuditor);
    this.bedCleansingRequestService.delete(id);
  }
}
