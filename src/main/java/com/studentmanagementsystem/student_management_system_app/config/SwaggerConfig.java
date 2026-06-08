package com.studentmanagementsystem.student_management_system_app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

  private static final String SECURITY_SCHEME_BEARER = "authorizationBearerTokenAccess";

  @Bean
  public OpenAPI openAPICustom() {
    return new OpenAPI().info(
                                new Info().title("Student Management System App API").description("REST API documentation for Student Management System App").version("v1")
                                          .contact(new Contact().name("Student Management System").email("support@studentmanagementsystem.com"))
                                          .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                        .externalDocs(new ExternalDocumentation().description("Project README").url("https://example.com"))
                        .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_BEARER)).components(
                    new Components().addSecuritySchemes(SECURITY_SCHEME_BEARER,
                                                        new SecurityScheme().name(SECURITY_SCHEME_BEARER).type(SecurityScheme.Type.HTTP).scheme("bearer")
                                                                            .bearerFormat("JWT").in(SecurityScheme.In.HEADER))).servers(
                    List.of(new Server().url("http://localhost:8080").description("Local dev"),
                            new Server().url("http://localhost:8081").description("Local prod")));
  }
}
