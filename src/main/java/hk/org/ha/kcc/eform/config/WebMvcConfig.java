package hk.org.ha.kcc.eform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hk.org.ha.kcc.common.web.ServiceRequestInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final ServiceRequestInterceptor serviceRequestInterceptor;

  public WebMvcConfig(ServiceRequestInterceptor transactionIdInterceptor) {
    this.serviceRequestInterceptor = transactionIdInterceptor;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/swagger-ui.html");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(this.serviceRequestInterceptor);
  }
}
