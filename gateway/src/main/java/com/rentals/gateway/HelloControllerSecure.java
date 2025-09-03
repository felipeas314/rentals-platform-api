package com.rentals.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControllerSecure {

  @GetMapping("/hello-secure")
  public String hello() {
    return "hello from gateway (secure)";
  }
}
