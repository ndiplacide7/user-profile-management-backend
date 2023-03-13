package com.rw.kgl.bkdf.userprofilemanagement.controller;

import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.service.UserCredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserCredentialControllerTest {

  private UserCredentialController userCredentialController;

  @Mock private UserCredentialService userCredentialService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userCredentialController = new UserCredentialController(userCredentialService);
  }

  @Test
  public void testCreateUserCredential() throws UserNotFoundException, UsernameExistException {
    UserCredential userCredential = new UserCredential("testuser", "testpassword");
    when(userCredentialService.createUserCredential("testuser", "testpassword"))
        .thenReturn(userCredential);

    ResponseEntity<UserCredential> response = userCredentialController.register(userCredential);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userCredential, response.getBody());

    verify(userCredentialService, times(1)).createUserCredential("testuser", "testpassword");
  }

  //    @Test
  //    public void testResetPassword() throws EmailNotFoundException {
  //        ResponseEntity<HttpResponse> expectedResponse =
  // userCredentialController.response(HttpStatus.OK, "EMAIL SENTtest@example.com");
  //        when(userCredentialService.resetPassword(anyString())).thenReturn(true);
  //
  //        ResponseEntity<HttpResponse> response =
  // userCredentialController.resetPassword("test@example.com");
  //
  //        assertEquals(expectedResponse, response);
  //
  //        verify(userCredentialService, times(1)).resetPassword("test@example.com");
  //    }
}
