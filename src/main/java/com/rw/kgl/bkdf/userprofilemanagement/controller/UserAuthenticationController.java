package com.rw.kgl.bkdf.userprofilemanagement.controller;

import com.rw.kgl.bkdf.userprofilemanagement.domain.AuthenticationRequest;
import com.rw.kgl.bkdf.userprofilemanagement.domain.AuthenticationResponse;
import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class UserAuthenticationController {
  private final List<UserCredential> users =
      Arrays.asList(
          new UserCredential(
              UUID.randomUUID(), "testusername1", "testpassword1", null, new Date(), null),
          new UserCredential(
              UUID.randomUUID(), "testusername2", "testpassword2", null, new Date(), null));
  @Autowired UserCredentialService userCredentialService;

  /**
   * ============== Authenticate user =============
   *
   * @param authenticationRequest
   * @return
   */
  @PostMapping("/api/authenticate")
  public ResponseEntity<?> authenticateUser(
      @RequestBody AuthenticationRequest authenticationRequest) {
    // Find the user with the given username and password
    UserCredential user =
        users.stream()
            .filter(
                u ->
                    u.getUsername().equals(authenticationRequest.getUsername())
                        && u.getPassword().equals(authenticationRequest.getPassword()))
            .findFirst()
            .orElse(null);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthenticationResponse("Invalid username or password"));
    }

    // Generate a JWT token with the user's id and a secret key
    String token = generateToken(user.getUserId().toString());

    // Return the token in the response
    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

  String generateToken(String userId) {
    return "Tokennnnnnn";
  }
}
