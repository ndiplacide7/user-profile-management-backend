package com.rw.kgl.bkdf.userprofilemanagement;

import com.rw.kgl.bkdf.userprofilemanagement.filter.JwtAuthenticationEntryPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UserProfileManagementBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserProfileManagementBackendApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
    return new JwtAuthenticationEntryPoint();
  }
}
