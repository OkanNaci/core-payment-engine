package com.deneme.payment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Secure Payment Platform API")
                        .version("v1.0")
                        .description("High-performance payment and order processing backend service.")
                        .contact(new Contact()
                                .name("Backend Engineering Team")
                                .email("dev@securepayment.com")));
    }
}