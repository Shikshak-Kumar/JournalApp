package com.example.JournalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Journal App APIs")
                                .description("By Shikshak Kumar")
                                .version("1.0")
                )
                .servers(
                        List.of(
                                new Server()
                                        .url("http://localhost:8080")
                                        .description("Local Server"),
                                new Server()
                                        .url("https://journalapp-hjyx.onrender.com")
                                        .description("Live Server")
                        )
                )
                .tags(List.of(
                        new Tag().name("Public APIs").description("Open endpoints"),
                        new Tag().name("User APIs").description("User operations"),
                        new Tag().name("Journal APIs").description("Journal management"),
                        new Tag().name("Admin APIs").description("Admin controls")
                ))

                // JWT Security
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .in(SecurityScheme.In.HEADER)
                                                .name("Authorization")
                                )
                )

                // Apply JWT globally
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                );
    }
}