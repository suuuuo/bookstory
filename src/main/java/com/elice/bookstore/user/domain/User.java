package com.elice.bookstore.user.domain;

import com.elice.bookstore.config.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String userName;

  @Column(unique = true)
  private String userId;

  @Column
  private String password;

  @Column
  private LocalDate dateOfBirth;

  @Column(unique = true)
  private String email;

  @Column
  private String phoneNumber;

  @Column
  private String address;

  @Column
  private Long point;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column
  private Boolean isExist;

  /**
   * user constructor except primary key(Long id).

   * @param userName .
   * @param userId user login id.
   * @param password .
   * @param dateOfBirth .
   * @param email .
   * @param phoneNumber .
   * @param address .
   * @param point can use points to buy books.
   * @param role two types: ADMIN, USER
   * @param isExist false if the user has been deleted.
   */
  public User(String userName, String userId, String password, LocalDate dateOfBirth, String email,
              String phoneNumber, String address, Long point, Role role, Boolean isExist) {
    this.userName = userName;
    this.userId = userId;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.point = point;
    this.role = role;
    this.isExist = isExist;
  }
}
