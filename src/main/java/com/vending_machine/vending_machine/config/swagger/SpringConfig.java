package com.vending_machine.vending_machine.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public OpenAPI swaggerAPI(){
        return new OpenAPI().info(new Info().title("Vending API")
                .description("Vending API")
                .version("1.0"));
    }
}
