package com.rentals.gateway;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthController {

  @GetMapping("/health")
  public Mono<String> hello() {
    return Mono.just("Ola");
  }
}
