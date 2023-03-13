package com.rw.kgl.bkdf.userprofilemanagement.controller;

import com.rw.kgl.bkdf.userprofilemanagement.domain.User;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailExistException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class UserProfileControllerTest {

  @Mock private UserService userService;

  @InjectMocks private UserProfileController userProfileController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Create user profile - Success")
  public void testCreateUserProfileSuccess()
      throws UserNotFoundException, UsernameExistException, EmailExistException {
    User newUser = new User();
    newUser.setFirstName("John");
    newUser.setLastName("Doe");
    newUser.setUsername("johndoe");
    newUser.setEmail("johndoe@example.com");

    when(userService.createUserProfile("John", "Doe", "johndoe", "johndoe@example.com"))
        .thenReturn(newUser);

    ResponseEntity<User> responseEntity = userProfileController.register(newUser);

    verify(userService, times(1))
        .createUserProfile("John", "Doe", "johndoe", "johndoe@example.com");

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(newUser, responseEntity.getBody());
  }

  @Test
  @DisplayName("Edit user profile - Success")
  public void testEditUserProfileSuccess()
      throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
    User updatedUser = new User();
    updatedUser.setFirstName("John");
    updatedUser.setLastName("Doe");
    updatedUser.setUsername("johndoe");
    updatedUser.setEmail("johndoe@example.com");

    when(userService.editUser(
            "johndoe", "John", "Doe", "johndoe", "johndoe@example.com", null, false, true, null))
        .thenReturn(updatedUser);

    ResponseEntity<User> responseEntity =
        userProfileController.update(
            "johndoe",
            "John",
            "Doe",
            "johndoe",
            "johndoe@example.com",
            null,
            "true",
            "false",
            null);

    verify(userService, times(1))
        .editUser(
            "johndoe", "John", "Doe", "johndoe", "johndoe@example.com", null, false, true, null);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(updatedUser, responseEntity.getBody());
  }
}
//    @Test
//    @DisplayName("Get a list of all users - Success")
//    public void testGetAllUsersSuccess() {
//        List<User> users = new
