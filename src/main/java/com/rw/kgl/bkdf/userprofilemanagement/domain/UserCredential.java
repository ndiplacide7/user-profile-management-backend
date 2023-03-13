package com.rw.kgl.bkdf.userprofilemanagement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS_CREDENTIALS")
public class UserCredential {

  @Id
  @GeneratedValue
  private UUID userId;
  private String username;
  private String password;
  private String previousPassword;
  private Date createdDate;
  private Date lastChangeDate;

  public UserCredential(String testuser, String testpassword) {
  }
}
