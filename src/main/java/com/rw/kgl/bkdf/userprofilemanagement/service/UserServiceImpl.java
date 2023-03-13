package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.User;
import com.rw.kgl.bkdf.userprofilemanagement.domain.UserPrincipal;
import com.rw.kgl.bkdf.userprofilemanagement.enumeration.Role;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailExistException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.rw.kgl.bkdf.userprofilemanagement.enumeration.Role.ROLE_USER;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

  BCryptPasswordEncoder bCryptPasswordEncoder;
  private Logger LOGGER = LoggerFactory.getLogger(getClass());
  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByUsername(username);
    if (user == null) {
      LOGGER.error("User not found by username:" + username);
      throw new UsernameNotFoundException("User not found by username:" + username);
    } else {
      user.setLastLoginDateDisplay(user.getLastLoginDate());
      user.setLastLoginDate(new Date());
      userRepository.save(user);
      UserPrincipal userPrincipal = new UserPrincipal(user);
      LOGGER.info("Returning found user by username:" + username);
      return userPrincipal;
    }
  }

  @Override
  public User createUserProfile(String firstName, String lastName, String username, String email)
      throws UserNotFoundException, UsernameExistException, EmailExistException {
    validateNewUsernameAndEmail(null, username, email);
    User user = new User();
    user.setUserId(generateUserId());
    String password = generatePassword();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setUsername(username);
    user.setEmail(email);
    user.setJoinDate(new Date());
    user.setPassword(encodePassword(password));
    user.setActive(true);
    user.setNotLocked(true);
    user.setRole(ROLE_USER.name());
    user.setAuthorities(ROLE_USER.getAuthorities());
    user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
    userRepository.save(user);
    LOGGER.info("New user password: " + password);
    // Hano twahashyira logic yo kohereza pwd muri mail
    // emailService.sendNewPasswordEmail(firstName, password, email);
    return user;
  }

  @Override
  public User editUser(
      String currentUsername,
      String newFirstName,
      String newLastName,
      String newUsername,
      String newEmail,
      String role,
      boolean isNonLocked,
      boolean isActive,
      MultipartFile profileImage)
      throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
    User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
    currentUser.setFirstName(newFirstName);
    currentUser.setLastName(newLastName);
    currentUser.setUsername(newUsername);
    currentUser.setEmail(newEmail);
    currentUser.setActive(isActive);
    currentUser.setNotLocked(isNonLocked);
    currentUser.setRole(getRoleEnumName(role).name());
    currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
    userRepository.save(currentUser);
    saveProfileImage(currentUser, profileImage);
    return currentUser;
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }


  @Override
  public User findUserById(UUID id) {
    return userRepository.findUserById(id);
  }

  @Override
  public void deleteUser(String username) throws IOException {
    User user = userRepository.findUserByUsername(username);
    Path userFolder = Paths.get("folder" + user.getUsername()).toAbsolutePath().normalize();
    FileUtils.deleteDirectory(new File(userFolder.toString()));
    userRepository.deleteById(user.getId());
  }

  @Override
  public User findUserByUsername(String username) {
    return null;
  }

  @Override
  public User findUserByEmail(String email) {
    return null;
  }

  // Helper methods
  private User validateNewUsernameAndEmail(
      String currentUsername, String newUsername, String newEmail)
      throws UserNotFoundException, UsernameExistException, EmailExistException {
    User userByNewUsername = findUserByUsername(newUsername);
    User userByNewEmail = findUserByEmail(newEmail);
    if (StringUtils.isNotBlank(currentUsername)) {
      User currentUser = findUserByUsername(currentUsername);
      if (currentUser == null) {
        throw new UserNotFoundException("No user found" + currentUsername);
      }
      if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
        throw new UsernameExistException("Username already exists");
      }
      if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
        throw new EmailExistException("Email already exists");
      }
      return currentUser;
    } else {
      if (userByNewUsername != null) {
        throw new UsernameExistException("Username already exists");
      }
      if (userByNewEmail != null) {
        throw new EmailExistException("Email already exists");
      }
      return null;
    }
  }

  private Role getRoleEnumName(String role) {
    return Role.valueOf(role.toUpperCase());
  }

  private UUID generateUserId() {
    UUID uuid = UUID.randomUUID();
    return uuid;
  }

  private String generatePassword() {
    return RandomStringUtils.randomAlphanumeric(15);
  }

  private String getTemporaryProfileImageUrl(String username) {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("path ya photo .." + username)
        .toUriString();
  }

  private String encodePassword(String password) {
    // Logic for Hashing Password;
    //    return passwordEncoder.encode(password);
    return "SampleEncoded!P#ssw00rd";
  }

  private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
    // Here I can put logic to save profile Image
  }
}
