package hk.org.ha.kcc.project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "KCC Spring Boot Service", version = "1.0"))
@SecurityScheme(name = "JWT", type = SecuritySchemeType.HTTP, scheme = "bearer",
    bearerFormat = "JWT")
public class OpenApiConfig {
}
