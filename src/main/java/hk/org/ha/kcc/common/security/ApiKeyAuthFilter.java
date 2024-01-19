package hk.org.ha.kcc.common.security;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private ApiKeyUtil apiKeyUtil;

    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    public ApiKeyAuthFilter(ApiKeyUtil apiKeyUtil) {
        this.apiKeyUtil = apiKeyUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //log.debug("doFilterInternal");
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            String requestApiKey = getApiKey((HttpServletRequest) request);

            if (requestApiKey != null) {
                if (apiKeyUtil.validateApiKey(requestApiKey)) {
                    ApiKeyAuthToken apiToken = new ApiKeyAuthToken(requestApiKey, AuthorityUtils.NO_AUTHORITIES);
                    SecurityContextHolder.getContext().setAuthentication(apiToken);
                } else {
                    response.sendError(401, "Invalid API Key.");
                    log.warn("Invalid API Key");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getApiKey(HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("api-key");

        if (authHeader != null) {
            return authHeader.trim();
        }

        return null;
    }


}