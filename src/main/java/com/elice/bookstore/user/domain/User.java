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
import lombok.Setter;

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

  @Column
  private String userId;

  @Column
  private LocalDate dateOfBirth;

  @Column
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
}
