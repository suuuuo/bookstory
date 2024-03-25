package com.elice.bookstore.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * user domain.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String userName;

  private LocalDate dateOfBirth;

  @Column(nullable = false, unique = true)
  private String nickName;

  @Column(nullable = false, unique = true)
  private String email;

  private String phoneNumber;

  @Column(nullable = false)
  private Long point;

  @Column(nullable = false)
  private Boolean isExist;

  /**
   * generate without primary key(id).
   */
  public User(String userName, LocalDate dateOfBirth, String nickName,
              String email, String phoneNumber, Long point, Boolean isExist) {
    this.userName = userName;
    this.dateOfBirth = dateOfBirth;
    this.nickName = nickName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.point = point;
    this.isExist = isExist;
  }
}