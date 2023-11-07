package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.MenuDto;
import hk.org.ha.kcc.its.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "menu", description = "menu API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@RequestMapping(MenuController.BASE_URL)
public class MenuController {
    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/menu/services";

    private final AuditorAware<String> auditorAware;


    private final MenuService menuService;

    public MenuController(AuditorAware<String> auditorAware, MenuService menuService) {
        this.auditorAware = auditorAware;
        this.menuService = menuService;
    }

    // post menu-service
    @Operation(summary = "Create new menu-service")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDto createMenu(@RequestBody MenuDto menuDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("createMenu by : " + currentAuditor);
        return this.menuService.create(menuDto);
    }

    // patch menu-service by id
    @Operation(summary = "Update menu-service by ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuDto updateMenu(@PathVariable Integer id, @RequestBody MenuDto menuDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("updateMenu id: " + id + " by : " + currentAuditor);
        return this.menuService.updateById(id, menuDto);
    }

    // get menu-service  by all
    @Operation(summary = "Get all menu-service")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuDto> getAllMenu() {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("getAllMenu by : " + currentAuditor);
        return this.menuService.getAllDto();
    }

    // get menu-service by id
    @Operation(summary = "Get menu-service by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuDto getMenuById(@PathVariable Integer id) {
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
