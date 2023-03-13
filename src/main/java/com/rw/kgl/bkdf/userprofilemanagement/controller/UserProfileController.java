package com.rw.kgl.bkdf.userprofilemanagement.controller;

import com.rw.kgl.bkdf.userprofilemanagement.domain.HttpResponse;
import com.rw.kgl.bkdf.userprofilemanagement.domain.User;
import com.rw.kgl.bkdf.userprofilemanagement.exception.ExceptionHandling;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailExistException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserProfileController extends ExceptionHandling {

  @Autowired private UserService userService;

  /**
   * -------------- API to Create user profile --------------
   *
   * @param user
   * @return
   * @throws UserNotFoundException
   * @throws UsernameExistException
   * @throws EmailExistException
   */
  @PostMapping("/create_user_profile")
  public ResponseEntity<User> register(@RequestBody User user)
      throws UserNotFoundException, UsernameExistException, EmailExistException {
    User newUser =
        userService.createUserProfile(
            user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
    return new ResponseEntity<>(newUser, OK);
  }

  /**
   * -------------------API to Edit user profile
   *
   * @param currentUsername
   * @param firstName
   * @param lastName
   * @param username
   * @param email
   * @param role
   * @param isActive
   * @param isNonLocked
   * @param profileImage
   * @return
   * @throws UserNotFoundException
   * @throws UsernameExistException
   * @throws EmailExistException
   * @throws IOException
   */
  @PostMapping("/edit_user_profile")
  public ResponseEntity<User> update(
      @RequestParam("currentUsername") String currentUsername,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName,
      @RequestParam("username") String username,
      @RequestParam("email") String email,
      @RequestParam("role") String role,
      @RequestParam("isActive") String isActive,
      @RequestParam("isNonLocked") String isNonLocked,
      @RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
      throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
    User updatedUser =
        userService.editUser(
            currentUsername,
            firstName,
            lastName,
            username,
            email,
            role,
            Boolean.parseBoolean(isNonLocked),
            Boolean.parseBoolean(isActive),
            profileImage);
    return new ResponseEntity<>(updatedUser, OK);
  }

  /**
   * -------------API to Get a list of all users
   *
   * @return
   */
  @GetMapping("/get_a_list_of_all_users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getUsers();
    return new ResponseEntity<>(users, OK);
  }

  @GetMapping("/find/{userId}")
  public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {

    String uuidString = userId;

    UUID uuidUserId = UUID.fromString(uuidString);
    User user = userService.findUserById(uuidUserId);
    return new ResponseEntity<>(user, OK);
  }

  /**
   * --------------API to ▪ Delete/Suspend user profile ------------------
   *
   * @param username
   * @return
   * @throws IOException
   */
  @DeleteMapping("/delete/{username}")
  @PreAuthorize("hasAnyAuthority('user:delete')")
  public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username)
      throws IOException {
    userService.deleteUser(username);
    return response(OK, "USER DELETED SUCCESSFULLY");
  }

  /**
   * --------API added for test purpose---------
   *
   * @return
   */
  @GetMapping("/home")
  public User viewUserDetails() {

    UUID uuid = UUID.randomUUID();

    User user = new User();
    user.setId(1l);
    user.setUserId(uuid);
    user.setFirstName("Placide");
    user.setLastName("Nduwayezu");
    user.setUsername("placido");
    user.setPassword(null);
    user.setActive(true);
    user.setNotLocked(false);
    user.setJoinDate(new Date());
    user.setRole(null);

    return user;
  }

  // Helper Methods
  private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
    return new ResponseEntity<>(
        new HttpResponse(
            httpStatus.value(),
            httpStatus,
            httpStatus.getReasonPhrase().toUpperCase(),
            message,
            new Date()),
        httpStatus);
  }
}
