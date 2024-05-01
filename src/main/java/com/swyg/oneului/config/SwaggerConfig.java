package com.swyg.oneului.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(apiSecurityRequirement())
                .components(apiComponents())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("OneulUi API Document")
                .description("OneulUi API Document")
                .version("1.0.0");
    }

    private SecurityRequirement apiSecurityRequirement() {
        return new SecurityRequirement()
                .addList("Authorization");
    }

    private Components apiComponents() {
        return new Components()
                .addSecuritySchemes("Authorization", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }
}