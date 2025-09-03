package com.rentals.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
public class JwtAuthConfig {

  @Bean
  public Converter<Jwt, Mono<AbstractAuthenticationToken>> reactiveJwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(jwt -> (Collection<GrantedAuthority>) extractRealmAuthorities(jwt));
    return new ReactiveJwtAuthenticationConverterAdapter(converter);
  }

  @SuppressWarnings("unchecked")
  private Collection<? extends GrantedAuthority> extractRealmAuthorities(Jwt jwt) {
    ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    Map<String, Object> realm = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());
    Collection<String> roles = (Collection<String>) realm.getOrDefault("roles", List.of());
    for (String r : roles) {
      // se vier "ROLE_HOST", mantém; se viesse "host", prefixaríamos com "ROLE_"
      String role = r.startsWith("ROLE_") ? r : "ROLE_" + r.toUpperCase();
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }
}
