package hk.org.ha.kcc.common.security;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final AlsXLogger log =
            AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String message = authException.getMessage();
        if (authException instanceof BadCredentialsException) {
            message = "Wrong username or password";
        }
        log.warn("Unauthorized error: {}", message);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }
}
