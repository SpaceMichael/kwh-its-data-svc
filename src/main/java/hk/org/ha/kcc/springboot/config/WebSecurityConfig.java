package hk.org.ha.kcc.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hk.org.ha.kcc.common.security.JwtAuthenticationEntryPoint;
import hk.org.ha.kcc.common.security.JwtTokenFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtTokenFilter jwtTokenFilter;

  public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenFilter jwtTokenFilter) {
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtTokenFilter = jwtTokenFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Set permissions on endpoints
    http.antMatcher("/api/**").authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
    // Add JWT token filter
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    // Set unauthorized requests exception handler
    http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    // Set session management to stateless
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // Enable CORS and disable CSRF
    http.cors().and().csrf().disable();
  }

  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    // Remove the ROLE_ prefix
    return new GrantedAuthorityDefaults("");
  }
}
