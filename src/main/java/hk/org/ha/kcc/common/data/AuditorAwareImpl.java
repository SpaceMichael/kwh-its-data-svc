package hk.org.ha.kcc.common.data;

import java.util.Optional;

import hk.org.ha.kcc.common.security.ApiKeyAuthToken;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(context -> (JwtAuthenticationToken) context.getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(authentication -> authentication.getTokenAttributes().get("preferred_username").toString());

    }
    /*public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> {
                    if (authentication instanceof JwtAuthenticationToken) {
                        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
                        // Extract the information from the JwtAuthenticationToken
                        return jwtAuthentication.getName();
                    } else if (authentication instanceof ApiKeyAuthToken) {
                        ApiKeyAuthToken apiKeyAuth = (ApiKeyAuthToken) authentication;
                        // Extract the information from the ApiKeyAuthToken
                        return apiKeyAuth.getName();
                    } else {
                        // Handle other types of authentication tokens or throw an exception
                        throw new IllegalArgumentException("Unsupported authentication token type: " + authentication.getClass().getName());
                    }
                });
    }*/


}
