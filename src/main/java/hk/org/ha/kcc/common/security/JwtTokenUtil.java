package hk.org.ha.kcc.common.security;

import java.lang.invoke.MethodHandles;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  private static final AlsXLogger log =
          AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

  private static final String JWT_ID_KEY = "jti";
  private static final String NAME_KEY = "name";
  private static final String HOSP_ID_KEY = "hospId";
  private static final String DEPT_ID_KEY = "deptId";
  private static final String DEPT_DSP_CODE_KEY = "deptDspCode";
  private static final String POST_ID_KEY = "postId";
  private static final String ROLES_KEY = "roles";

  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Value("${app.jwt.expirationMs}")
  private int jwtExpirationMs;
  public JwtAuthenticationToken convert(Jwt jwt) {
    Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
    Collection<String> roles = realmAccess.get("roles");
    var grantedAuthorities = roles.stream()
      .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
      .collect(Collectors.toList());
    return new JwtAuthenticationToken(jwt, grantedAuthorities);
  }

  public String generateJwtToken(String username, String password, String roles) {
    List<GrantedAuthority> authorities = Arrays.asList(roles.split(",")).stream()
            .map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
    UserDetails userDetails =
            User.builder().username(username).password(password).authorities(authorities).build();
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password,
            userDetails.getAuthorities());
    return this.generateJwtToken(authentication);
  }

  public String generateJwtToken(Authentication authentication) {
    String subject = authentication.getName();
    String roles = authentication.getAuthorities().stream()
            .map(authority -> authority.getAuthority()).collect(Collectors.joining(","));

    Date issueDate = new Date();
    Date expirationDate = new Date((new Date()).getTime() + this.jwtExpirationMs);

    if (authentication.getPrincipal() instanceof UserDetailsImpl) {
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      userDetails.setJwtId(UUID.randomUUID().toString());
      return Jwts.builder().setSubject(subject).setExpiration(expirationDate).setIssuedAt(issueDate)
              .claim(JWT_ID_KEY, userDetails.getJwtId()).claim(NAME_KEY, userDetails.getName())
              .claim(HOSP_ID_KEY, userDetails.getHospId()).claim(DEPT_ID_KEY, userDetails.getDeptId())
              .claim(DEPT_DSP_CODE_KEY, userDetails.getDeptDspCode())
              .claim(POST_ID_KEY, userDetails.getPostId()).claim(ROLES_KEY, roles)
              .signWith(this.getSecretKey()).compact();
    } else {
      return Jwts.builder().setSubject(subject).setExpiration(expirationDate).setIssuedAt(issueDate)
              .claim(JWT_ID_KEY, UUID.randomUUID().toString()).claim(ROLES_KEY, roles)
              .signWith(this.getSecretKey()).compact();
    }
  }

  public UsernamePasswordAuthenticationToken getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(this.getSecretKey()).build()
            .parseClaimsJws(token).getBody();
    String jwtId = Objects.toString(claims.get(JWT_ID_KEY), null);
    String name = Objects.toString(claims.get(NAME_KEY), null);
    String hospId = Objects.toString(claims.get(HOSP_ID_KEY), null);
    String deptId = Objects.toString(claims.get(DEPT_ID_KEY), null);
    String postId = Objects.toString(claims.get(POST_ID_KEY), null);

    List<GrantedAuthority> authorities =
            Arrays.asList(Objects.toString(claims.get(ROLES_KEY), "").split(",")).stream()
                    .map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
    UserDetails userDetails = UserDetailsImpl.builder().username(claims.getSubject()).jwtId(jwtId)
            .name(name).hospId(hospId).deptId(deptId).postId(postId).authorities(authorities).build();

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(this.getSecretKey()).build().parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.warn("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.warn("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.warn("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.warn("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.warn("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  private Key getSecretKey() {
    byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
