package com.elice.bookstore.cart.domain;

import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.config.audit.BaseTimeEntity;
import com.elice.bookstore.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * cart domain.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Cart(User user){
    this.user = user;
  }
}