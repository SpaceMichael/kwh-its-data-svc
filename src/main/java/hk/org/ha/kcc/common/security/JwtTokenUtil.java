package hk.org.ha.kcc.common.security;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  public JwtAuthenticationToken convert(Jwt jwt) {
    Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
    Collection<String> roles = realmAccess.get("roles");
    var grantedAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
    return new JwtAuthenticationToken(jwt, grantedAuthorities);
  }
}
