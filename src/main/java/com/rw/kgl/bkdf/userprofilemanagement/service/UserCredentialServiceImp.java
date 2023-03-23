package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.repository.UserCredentialRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserCredentialServiceImp implements UserCredentialService {
  Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Autowired
  UserCredentialRepository userCredentialRepository;

  @Override
  public UserCredential createUserCredential(String username, String password)
      throws UserNotFoundException, UsernameExistException {
    // Logic to check different things before creating user credential
    UserCredential newUserCredential = new UserCredential();
    newUserCredential.setUsername(username);
    newUserCredential.setPassword(generatePassword());
    newUserCredential.setCreatedDate(new Date());
    newUserCredential.setUserId(
        UUID.randomUUID()); // Here we can improve this logic to generate userId
    LOGGER.info("BEFORE SAVE............."+newUserCredential);
    userCredentialRepository.save(newUserCredential);
    return newUserCredential;
  }

  @Override
  public void resetPassword(String email) throws EmailNotFoundException {}

  @Override
  public UserCredential findUserByUsername(String username) {
    return userCredentialRepository.findUserByUsername(username);
  }

  // Helper methods
  private String generatePassword() {
    return RandomStringUtils.randomAlphanumeric(15);
  }
}
