package hk.org.ha.kcc.its.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient kwhItsWebClient(@Value("${kwh.its.url}") String url,
                                     @Value("${kwh.its.key}") String key,
                                     WebClient.Builder webClientBuilder
    ) {
        return webClientBuilder
                .baseUrl(url)
                .defaultHeader("X-Gravitee-Api-Key", key)
                .build();
    }
}
