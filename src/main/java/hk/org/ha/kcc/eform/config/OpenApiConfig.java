package hk.org.ha.kcc.eform.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(title = "KWH ITS (Integration Telecommunication System) Data Repository Service",
        version = "1.0"),
    servers = {@Server(url = "/", description = "Default Server URL")})
@SecurityScheme(name = "JWT", type = SecuritySchemeType.HTTP, scheme = "bearer",
    bearerFormat = "JWT")
public class OpenApiConfig {

}
