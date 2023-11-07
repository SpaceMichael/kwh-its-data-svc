package hk.org.ha.kcc.its.controller;

import java.lang.invoke.MethodHandles;

import hk.org.ha.kcc.its.dto.MenuServiceDto;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "menu-service", description = "menu-service API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(MenuServiceController.BASE_URL)
public class MenuServiceController {

    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/menu/services";

    private final AuditorAware<String> auditorAware;

    private final MenuService menuService;

    public MenuServiceController(AuditorAware<String> auditorAware, MenuService menuService) {
        this.auditorAware = auditorAware;
        this.menuService = menuService;
    }

    // post menu-service
    @Operation(summary = "Create new menu-service")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuServiceDto createMenu(@RequestBody MenuServiceDto menuServiceDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("createMenu by : " + currentAuditor);
        return this.menuService.create(menuServiceDto);
    }

    // patch menu-service by id
    @Operation(summary = "Update menu-service by ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuServiceDto updateMenu(@PathVariable Integer id, @RequestBody MenuServiceDto menuServiceDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("updateMenu id: " + id + " by : " + currentAuditor);
        return this.menuService.updateById(id, menuServiceDto);
    }

    // get menu-service  by all
    @Operation(summary = "Get all menu-service")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<MenuServiceDto> getAllMenu() {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("getAllMenu by : " + currentAuditor);
        return this.menuService.getAllDto();
    }

    // get menu-service by id
    @Operation(summary = "Get menu-service by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuServiceDto getMenuById(@PathVariable Integer id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("getMenuById id:" + id + " by : " + currentAuditor);
        return this.menuService.getDtoById(id);
    }

    //delete menu-service by id
    @Operation(summary = "Delete menu-service by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMenuById(@PathVariable Integer id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("deleteMenuById id:" + id + " by : " + currentAuditor);
        this.menuService.deleteById(id);
    }
}
