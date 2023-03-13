package com.rw.kgl.bkdf.userprofilemanagement.repository;

import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID> {
    UserCredential findUserByUsername(String username);
}
