package com.rentals.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  private final org.springframework.core.convert.converter.Converter
    <org.springframework.security.oauth2.jwt.Jwt,
      reactor.core.publisher.Mono<org.springframework.security.authentication.AbstractAuthenticationToken>>
    jwtConverter;

  public SecurityConfig(
    org.springframework.core.convert.converter.Converter<
      org.springframework.security.oauth2.jwt.Jwt,
      reactor.core.publisher.Mono<org.springframework.security.authentication.AbstractAuthenticationToken>> jwtConverter) {
    this.jwtConverter = jwtConverter;
  }

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http
      .csrf(ServerHttpSecurity.CsrfSpec::disable)
      .authorizeExchange(ex -> ex
        .pathMatchers("/actuator/**").permitAll()
        .pathMatchers("/hello-secure").authenticated()

        // regras de negócio:
        // users: leitura para qualquer autenticado; escrita só ADMIN
        .pathMatchers("GET",    "/api/users/**").authenticated()
        .pathMatchers("POST",   "/api/users/**").hasRole("ADMIN")
        .pathMatchers("PUT",    "/api/users/**").hasRole("ADMIN")
        .pathMatchers("DELETE", "/api/users/**").hasRole("ADMIN")

        // listings: criar/editar para HOST/ADMIN; leitura pública via gateway? (aqui exijo auth)
        .pathMatchers("GET",  "/api/listings/**").authenticated()
        .pathMatchers("POST", "/api/listings/**").hasAnyRole("HOST","ADMIN")
        .pathMatchers("PUT",  "/api/listings/**").hasAnyRole("HOST","ADMIN")
        .pathMatchers("DELETE","/api/listings/**").hasAnyRole("HOST","ADMIN")

        .anyExchange().authenticated()
      )
      .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
      .build();
  }
}
