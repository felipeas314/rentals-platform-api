package com.rentals.users.repository;

import com.rentals.users.domain.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserAccount, String> {
  Optional<UserAccount> findByEmail(String email);
}
