package com.project.ClientDesk.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clientDeskAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ClientDesk API")
                        .version("1.0")
                        .description("Client Management System for Digital Marketing Agencies")
                        .contact(new Contact()
                                .name("Irfan Hameed")
                                .email("irfanhameedv@gmail.com")));
    }
}

