package com.rw.kgl.bkdf.userprofilemanagement.service;

import com.rw.kgl.bkdf.userprofilemanagement.domain.UserCredential;
import com.rw.kgl.bkdf.userprofilemanagement.repository.UserCredentialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    UserCredentialService userCredentialService;
    @Override
    public String authenticateUser(String username, String password) {
        return "okasdsa";
    }

    @Override
    public UserCredential findUserByUsername(String username) {
        return userCredentialService.findUserByUsername(username) ;
    }
}
