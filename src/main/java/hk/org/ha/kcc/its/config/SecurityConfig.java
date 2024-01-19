package hk.org.ha.kcc.its.config;

import hk.org.ha.kcc.common.security.ApiKeyAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import hk.org.ha.kcc.common.security.JwtAuthenticationEntryPoint;
import hk.org.ha.kcc.common.security.JwtTokenUtil;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenUtil jwtTokenUtil;
    private final ApiKeyAuthFilter apiKeyAuthFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtTokenUtil jwtTokenUtil, ApiKeyAuthFilter apiKeyAuthFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenUtil = jwtTokenUtil;
        this.apiKeyAuthFilter = apiKeyAuthFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.antMatcher("/api/v1/**").authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
        http.securityMatchers(matcher -> matcher.requestMatchers("/api/v1/**"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //http.cors().and().csrf().disable();
                .cors(Customizer.withDefaults())
                //http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtTokenUtil::convert).and().authenticationEntryPoint(jwtAuthenticationEntryPoint);
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtTokenUtil::convert)).authenticationEntryPoint(jwtAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain WebhookfilterChain(HttpSecurity http) throws Exception {
        //http.antMatcher("/api/v2/webhook/*").authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
        http.securityMatchers(matchers -> matchers.requestMatchers("/api/v2/webhook/*"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //http.cors().and().csrf().disable();
                .cors(Customizer.withDefaults())
                // Disable CSRF
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
