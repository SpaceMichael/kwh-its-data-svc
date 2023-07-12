package hk.org.ha.kcc.eforms.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("test")
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories("hk.org.ha.kcc.eforms.repository")
@EntityScan("hk.org.ha.kcc.eforms.model")
public class JpaTestConfig {

  public static final String AUDITOR = "tester";

  @Bean
  AuditorAware<String> auditorProviderTest() {
    return () -> Optional.of(AUDITOR);
  }
}