package br.com.matheusmf.challenge.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	
	@Bean
	OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Challenge - Mercado Livre")
                .version("v1")
                .description("Challenge - Mercado Livre"));
	}

}
