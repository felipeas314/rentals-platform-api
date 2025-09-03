package com.rentals.users.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDtos {

  public record Create(
    @Email @NotBlank String email,
    @NotBlank @Size(min = 2, max = 120) String name,
    @NotBlank String role
  ) {}

  public record Update(
    @NotBlank @Size(min = 2, max = 120) String name,
    @NotBlank String role
  ) {}

  public record View(String id, String email, String name, String role) {}
}
