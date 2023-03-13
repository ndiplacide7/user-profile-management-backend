package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;

public interface UserCredentialService {

    UserCredential createUserProfile(String firstName, String lastName, String username, String email)
            throws UserNotFoundException, UsernameExistException;

    void resetPassword(String email) throws EmailNotFoundException;

    UserCredential findUserByUsername(String username);


}
