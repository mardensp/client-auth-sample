package com.example.clientauthsample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.clientauthsample"))
                .paths(PathSelectors.any())
                .build()
                .groupName("v1")
                .apiInfo(new ApiInfoBuilder()
                        .version("1")
                        .title("Client Auth Sample")
                        .contact(new Contact("Marden S. Paiva", "", "mardensp@hotmail.com"))
                        .build());
    }
}
