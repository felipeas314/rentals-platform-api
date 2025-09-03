package com.rentals.users.controller;

import com.rentals.users.dto.UserDtos;
import com.rentals.users.domain.UserAccount;
import com.rentals.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService svc;
  public UserController(UserService svc) { this.svc = svc; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserAccount create(@RequestBody @Valid UserDtos.Create in) {
    return svc.create(in);
  }

  @PutMapping("/{id}")
  public UserAccount update(@PathVariable String id, @RequestBody @Valid UserDtos.Update in) {
    return svc.update(id, in);
  }

  @GetMapping("/{id}")
  public UserAccount get(@PathVariable String id) { return svc.get(id); }

  @GetMapping
  public List<UserAccount> list() { return svc.list(); }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) { svc.delete(id); }
}

