package vn.car_service.Car_Service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("dev")
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(@Value("${openapi.service.api-docs}") String apiDocs) {
        return GroupedOpenApi.builder()
                .group("apiDocs")
                .packagesToScan("package controller")
                .build();
    }

    @Bean
    public OpenAPI openAPI(@Value("${openapi.service.server}") String serverUrl,
                           @Value("${openapi.service.title}") String title,
                           @Value("${openapi.service.version}") String version) {
        return new OpenAPI()
                .servers(List.of(new Server().url(serverUrl)))
                .info(new Info().title(title).description("API-Document")
                        .version(version)
                        .license(new License().name("API license").url("https://springdoc.org")));
    }
}
