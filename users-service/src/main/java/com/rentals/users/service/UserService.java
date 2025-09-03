package com.rentals.users.service;

import com.rentals.users.dto.UserDtos;
import com.rentals.users.domain.UserAccount;
import com.rentals.users.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository repo;

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public UserAccount create(UserDtos.Create in) {
    var user = new UserAccount(null, in.email().toLowerCase(), in.name().trim(), in.role());
    try {
      return repo.save(user);
    } catch (DuplicateKeyException e) {
      throw new IllegalArgumentException("email já existe");
    }
  }

  public UserAccount update(String id, UserDtos.Update in) {
    var u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("user não encontrado"));
    u.setName(in.name().trim());
    u.setRole(in.role());
    return repo.save(u);
  }

  public UserAccount get(String id) {
    return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("user não encontrado"));
  }

  public List<UserAccount> list() {
    return repo.findAll();
  }

  public void delete(String id) {
    repo.deleteById(id);
  }
}
