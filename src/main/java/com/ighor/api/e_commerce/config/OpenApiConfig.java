package com.ighor.api.e_commerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "E-Commerce API",
                version = "1.0",
                description = "REST API para gerenciamento de e-commerce com autenticação JWT"
        )
)
public class OpenApiConfig {
}