package hk.org.ha.kcc.eform.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.eform.dto.MenuDto;
import hk.org.ha.kcc.eform.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@Tag(name = "Menu", description = "Menu API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(MenuController.BASE_URL)
public class MenuController {

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/menu";

    private final AuditorAware<String> auditorAware;

    private final MenuService menuService;

    public MenuController(AuditorAware<String> auditorAware, MenuService menuService) {
        this.auditorAware = auditorAware;
        this.menuService = menuService;
    }

    //post menu
    @Operation(summary = "Create new Menu")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDto createMenu(@RequestBody MenuDto menuDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("createMenu by : " + currentAuditor);
        return this.menuService.create(menuDto);
    }

    //patch menu by id
    @Operation(summary = "Update Menu by ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuDto updateMenu(@PathVariable Integer id, @RequestBody MenuDto menuDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("updateMenu by : " + currentAuditor);
        return this.menuService.update(id, menuDto);
    }



}
