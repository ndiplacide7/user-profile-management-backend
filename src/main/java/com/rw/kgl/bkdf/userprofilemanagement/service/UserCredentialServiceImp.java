package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.EmailNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UserNotFoundException;
import com.rw.kgl.bkdf.userprofilemanagement.exception.domain.UsernameExistException;
import com.rw.kgl.bkdf.userprofilemanagement.repository.UserCredentialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserCredentialServiceImp implements UserCredentialService{

    UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredential createUserProfile(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException {
        return null;
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException {

    }

    @Override
    public UserCredential findUserByUsername(String username) {
        return userCredentialRepository.findUserByUsername(username);
    }
}
