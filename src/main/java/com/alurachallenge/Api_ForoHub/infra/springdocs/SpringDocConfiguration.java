package com.alurachallenge.Api_ForoHub.infra.springdocs;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Api Rest Foro-Hub")
                        .description("API Rest de la aplicación foro-hub, que contiene las funcionalidades CRUD listar Topicos, actualizar,buscar,eliminar y registrar topicos")
                        .contact(new Contact()
                                .name("Richard Lipa")
                                .email("rlipac31@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://foro-hub/api/licencia")));
    }
}
