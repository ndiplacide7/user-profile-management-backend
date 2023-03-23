package com.rw.kgl.bkdf.userprofilemanagement.controller;

import com.rw.kgl.bkdf.userprofilemanagement.domain.HttpResponse;
import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/", "/user_credential"})
public class UserCredentialController {
  @Autowired UserCredentialService userCredentialService;

  public UserCredentialController(UserCredentialService userCredentialService) {
    this.userCredentialService = userCredentialService;
  }

    /**
     * -------------API to Create user credentials
     *
     * @param userCredential
     * @return
     * @throws UserNotFoundException
     * @throws UsernameExistException
     */
  @PostMapping("/create_user_credential")
  public ResponseEntity<UserCredential> register(@RequestBody UserCredential userCredential)
      throws UserNotFoundException, UsernameExistException {
    UserCredential newUser =
        userCredentialService.createUserCredential(
            userCredential.getUsername(), userCredential.getPassword());

    return new ResponseEntity<>(newUser, OK);
  }

  /**
   * ---------- API to Change/reset password
   *
   * @param email
   * @return
   * @throws EmailNotFoundException
   */
  @GetMapping("/resetpassword/{email}")
  public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email)
      throws EmailNotFoundException {
    userCredentialService.resetPassword(email);
    return response(OK, "EMAIL SENT" + email);
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
