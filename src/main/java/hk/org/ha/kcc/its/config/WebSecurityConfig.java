package hk.org.ha.kcc.its.config;

import hk.org.ha.kcc.common.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import hk.org.ha.kcc.common.security.JwtAuthenticationEntryPoint;
import hk.org.ha.kcc.common.security.JwtTokenUtil;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class WebSecurityConfig {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtTokenFilter jwtTokenFilter;

  public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      JwtTokenUtil jwtTokenUtil) {
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtTokenUtil = jwtTokenUtil;
  }


  @Bean
  @Order(1)
  public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
    http.antMatcher("/api/v2/**")
            .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.cors().and().csrf().disable();
    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
    http.antMatcher("/api/v1/**")
        .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
    http.sessionManagement(
        management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.cors().and().csrf().disable();
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtTokenUtil::convert).and()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    return http.build();
  }


}
