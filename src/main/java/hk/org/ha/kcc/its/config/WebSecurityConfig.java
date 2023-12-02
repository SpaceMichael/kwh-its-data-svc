package hk.org.ha.kcc.its.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import hk.org.ha.kcc.common.security.JwtAuthenticationEntryPoint;
import hk.org.ha.kcc.common.security.JwtTokenUtil;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class WebSecurityConfig {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtTokenUtil jwtTokenUtil;

  public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      JwtTokenUtil jwtTokenUtil) {
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.antMatcher("/api/**")
        .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());

    http.sessionManagement(
        management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.cors().and().csrf().disable();

    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtTokenUtil::convert).and()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint);

    return http.build();
  }
}
