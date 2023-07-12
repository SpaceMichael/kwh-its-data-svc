package hk.org.ha.kcc.common.data;

import java.util.Optional;

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
}
