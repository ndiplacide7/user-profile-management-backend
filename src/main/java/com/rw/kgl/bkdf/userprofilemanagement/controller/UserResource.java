package com.rw.kgl.bkdf.userprofilemanagement.controller;

import com.rw.kgl.bkdf.userprofilemanagement.domain.User;
import com.rw.kgl.bkdf.userprofilemanagement.exception.ExceptionHandling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path = {"/","/user"})
public class UserResource extends ExceptionHandling {

  @GetMapping("/home")
  public User viewUserDetails()  {

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

//   throw new EmailExistException("Email has already been used!");
  }



}
