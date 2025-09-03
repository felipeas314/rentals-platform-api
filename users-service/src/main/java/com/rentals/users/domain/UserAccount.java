package com.rentals.users.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class UserAccount {

  @Id
  private String id;

  @Email @NotBlank
  @Indexed(unique = true)
  private String email;

  @NotBlank @Size(min = 2, max = 120)
  private String name;

  @NotBlank
  @Indexed
  private String role; // ROLE_GUEST | ROLE_HOST | ROLE_ADMIN

  // getters/setters/constructors
  public UserAccount() {}
  public UserAccount(String id, String email, String name, String role) {
    this.id = id; this.email = email; this.name = name; this.role = role;
  }
  public String getId() { return id; }
  public String getEmail() { return email; }
  public String getName() { return name; }
  public String getRole() { return role; }
  public void setId(String id) { this.id = id; }
  public void setEmail(String email) { this.email = email; }
  public void setName(String name) { this.name = name; }
  public void setRole(String role) { this.role = role; }
}
