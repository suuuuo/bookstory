package com.elice.bookstore.config.security.authentication.refresh.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * refresh auth domain.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Refresh {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String refresh;
  private String expiration;

  /**
   * refresh constructor.
   *
   * @param email if a user is issued multiple tokens, this is required to retrieve all tokens.
   * @param refresh .
   * @param expiration need to remove expired tokens.
   */
  public Refresh(String email, String refresh, String expiration) {
    this.email = email;
    this.refresh = refresh;
    this.expiration = expiration;
  }
}
