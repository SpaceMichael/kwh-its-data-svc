package hk.org.ha.kcc.its.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import hk.org.ha.kcc.its.dto.UserAccessDto;
import hk.org.ha.kcc.its.service.UserAccessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Tag(name = "user-access", description = "User Access API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(UserAccessController.BASE_URL)
public class UserAccessController {
    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/user_accesses";

    private final AuditorAware<String> auditorAware;
    private final UserAccessService userAccessService;

    // get all user-access-dto
    @Operation(summary = "Get list of UserAccessDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserAccessDto> getAllUserAccess() {
        log.debug("get all user access by: " + auditorAware.getCurrentAuditor().orElse("Unknown"));
        return this.userAccessService.getAllDto();
    }

    // post user access
    @Operation(summary = "Create new UserAccess")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAccessDto createUserAccess(@RequestBody UserAccessDto userAccessDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("create user access by: " + currentAuditor);
        return this.userAccessService.create(userAccessDto);
    }

    // patch user access by id
    @Operation(summary = "Update UserAccess by id")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserAccessDto updateUserAccess(@PathVariable String id, @RequestBody UserAccessDto userAccessDto) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("update user access by: " + currentAuditor);
        // check corpdId is null
        if (userAccessDto.getCorpId() != null) {
            userAccessDto.setCorpId(id);
        }
        return this.userAccessService.updateById(id, userAccessDto);
    }

    // get user access by id
    @Operation(summary = "Get UserAccess by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserAccessDto getUserAccessById(@PathVariable String id) {
        log.debug("get user access by id: " + id);
        return this.userAccessService.getDtoById(id);
    }

/*    // delete user by id
    @Operation(summary = "Delete UserAccess by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserAccessById(@PathVariable String id) {
        String currentAuditor = auditorAware.getCurrentAuditor().orElse("Unknown");
        log.debug("delete user access by: " + currentAuditor);
        this.userAccessService.deleteById(id);
    }*/
}
