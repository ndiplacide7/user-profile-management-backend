package com.rw.kgl.bkdf.userprofilemanagement.repository;

import com.rw.kgl.bkdf.userprofilemanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByUsername(String username);

  User findUserByEmail(String email);

    User findUserById(UUID id);
}
