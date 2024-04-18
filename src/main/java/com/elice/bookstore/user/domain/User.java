package com.elice.bookstore.user.domain;

import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.user.dto.RequestModifyUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * user domain.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

  /**
   * for SecurityContextHolder.

   * @param id .
   * @param role .
   */
  public User(String id, Role role) {
    this.id = Long.parseLong(id);
    this.role = role;
  }

  /**
   * modify user info.
   *
   * @param req .
   */
  public void modifyUser(RequestModifyUser req) {
    if (req.userName() != null && !req.userName().isBlank()) {
      this.userName = req.userName();
    }
    if (req.dateOfBirth() != null && !req.dateOfBirth().isBlank()) {
      this.dateOfBirth = LocalDate.parse(req.dateOfBirth());
    }
    if (req.email() != null && !req.email().isBlank()) {
      this.email = req.email();
    }
    if (req.phoneNumber() != null && !req.phoneNumber().isBlank()) {
      this.phoneNumber = req.phoneNumber();
    }
    if (req.address() != null && !req.address().isBlank()) {
      this.address = req.address();
    }
    if (req.point() != null) {
      this.point = req.point();
    }
    if (req.role() != null && !req.role().isBlank()) {
      this.role = Role.valueOf(req.role());
    }
  }

  public void deleteUser() {
    this.isExist = false;
  }
}