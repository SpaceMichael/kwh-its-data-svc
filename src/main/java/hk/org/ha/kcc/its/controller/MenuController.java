package hk.org.ha.kcc.its.controller;

import java.lang.invoke.MethodHandles;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.MenuDto;
import hk.org.ha.kcc.its.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Menu", description = "Menu API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(MenuController.BASE_URL)
public class MenuController {

  private static final AlsXLogger log =
      AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
  public static final String BASE_URL = "/api/v1/menu";

  private final AuditorAware<String> auditorAware;

  private final MenuService menuService;

  public MenuController(AuditorAware<String> auditorAware, MenuService menuService) {
    this.auditorAware = auditorAware;
    this.menuService = menuService;
  }

  // post menu
  @Operation(summary = "Create new Menu")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MenuDto createMenu(@RequestBody MenuDto menuDto) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("createMenu by : " + currentAuditor);
    return this.menuService.create(menuDto);
  }

  // patch menu by id
  @Operation(summary = "Update Menu by ID")
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public MenuDto updateMenu(@PathVariable Integer id, @RequestBody MenuDto menuDto) {
    String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
    log.debug("updateMenu by : " + currentAuditor);
    return this.menuService.update(id, menuDto);
  }
}
