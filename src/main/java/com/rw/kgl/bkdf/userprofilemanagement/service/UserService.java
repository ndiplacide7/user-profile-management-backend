package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.User;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailExistException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {

  User createUserProfile(String firstName, String lastName, String username, String email)
      throws UserNotFoundException, UsernameExistException, EmailExistException;

  User editUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

  List<User> getUsers();

  User findUserById(UUID id);

  void deleteUser(String username) throws IOException;





  User findUserByUsername(String username);

  User findUserByEmail(String email);
}
