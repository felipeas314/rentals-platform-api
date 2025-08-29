package com.rentals.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http
      .csrf(ServerHttpSecurity.CsrfSpec::disable)
      .authorizeExchange(exchange -> exchange
        .pathMatchers("/health").permitAll()  // libera o Hello
        .anyExchange().authenticated()       // o restante exige JWT
      )
      .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
      .build();
  }
}
