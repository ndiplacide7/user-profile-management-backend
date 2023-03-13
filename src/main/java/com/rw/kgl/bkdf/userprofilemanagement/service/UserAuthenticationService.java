package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;

public interface UserAuthenticationService {

  String authenticateUser(String username, String password);

  UserCredential findUserByUsername(String username);
}
