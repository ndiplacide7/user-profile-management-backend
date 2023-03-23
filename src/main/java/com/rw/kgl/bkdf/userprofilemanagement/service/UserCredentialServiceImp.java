package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.User;
import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.repository.UserCredentialRepository;
import com.rw.kgl.bkdf.userprofilemanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class UserCredentialServiceImp implements UserCredentialService {
  Logger LOGGER = LoggerFactory.getLogger(getClass());
  @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired UserCredentialRepository userCredentialRepository;

  @Autowired UserRepository userRepository;

  @Override
  public UserCredential createUserCredential(String username, String password)
      throws UserNotFoundException, UsernameExistException {
    // Logic to check different things before creating user credential

    // check if username already exists in user_profile
    User userProfileExists = userRepository.findUserByUsername(username);
    UserCredential userCredentialExists = userCredentialRepository.findUserByUsername(username);
    if (userProfileExists == null)
      throw new UserNotFoundException(
          "There is no user profile available with this username :" + username);

    if (userCredentialExists != null)
      throw new UsernameExistException(
          "User Credential for user " + username + " already exists. Please try with password reset");

    String newPassword;
    newPassword = generatePassword();
    String hashedPassword = encodePassword(newPassword);
    UserCredential newUserCredential = new UserCredential();
    newUserCredential.setUsername(username);
    newUserCredential.setPassword(hashedPassword);
    newUserCredential.setCreatedDate(new Date());
    newUserCredential.setUserId(
        userProfileExists.getUserId()); // Getting userId from user_profile table

    // hano nahashyira logic yo kohereza password kuri mail na phone ya user

    LOGGER.info("BEFORE SAVE............." + newUserCredential);
    LOGGER.info("newPassword............." + newPassword);
    LOGGER.info("Encoded password............." + hashedPassword);
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

  private String encodePassword(String password) {
    // Logic for Hashing Password;
    return bCryptPasswordEncoder.encode(password);
  }
}
