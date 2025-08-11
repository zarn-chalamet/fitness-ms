package com.fitness.ai_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI aiServiceAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("AI Recommendation Service API")
                        .description("AI Recommendation Service API for Ai Fitness Microservices")
                        .version("v1.0.0"));
    }
}
