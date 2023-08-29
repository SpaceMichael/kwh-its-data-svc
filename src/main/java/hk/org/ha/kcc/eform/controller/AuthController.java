package hk.org.ha.kcc.eform.controller;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;


@Tag(name = "auth", description = "auth API")
@SecurityRequirement(name = "JWT")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(AuthController.BASE_URL)
public class AuthController {
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/auths";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object getAuth() throws Exception {
        log.debug("Get Authentication object with valid JWT.");
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
