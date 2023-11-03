package hk.org.ha.kcc.its.controller;

import java.lang.invoke.MethodHandles;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.ConstantDto;
import hk.org.ha.kcc.its.service.ConstantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "constant", description = "constant API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(ConstantController.BASE_URL)
public class ConstantController {
  private static final AlsXLogger log =
      AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
  public static final String BASE_URL = "/api/v1/constants";

  private final ConstantService constantService;

  private final AuditorAware<String> auditorAware;

  public ConstantController(ConstantService constantService, AuditorAware<String> auditorAware) {
    this.constantService = constantService;
    this.auditorAware = auditorAware;
  }

  // post constant
  @Operation(summary = "Create new Constant")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ConstantDto createConstant(@RequestBody ConstantDto constantDto) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("createConstant by : " + currentAuditor);
    return this.constantService.create(constantDto);
  }

  // get constant by id
  @Operation(summary = "Get the Constant by id")
  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ConstantDto getConstantById(@PathVariable Integer id) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("getConstantById by: " + currentAuditor);
    return this.constantService.getDtoById(id);
  }
}
