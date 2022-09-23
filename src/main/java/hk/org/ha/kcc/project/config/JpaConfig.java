package hk.org.ha.kcc.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import hk.org.ha.kcc.common.data.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

  @Bean
  AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }
}
