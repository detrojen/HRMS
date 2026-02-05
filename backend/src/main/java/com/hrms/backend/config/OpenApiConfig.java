package com.hrms.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("rest-api-demo")
                        .version("x.1")
                        .description("employee rest endpoints to perform rest api demo"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("local")
                ));
    }
}
