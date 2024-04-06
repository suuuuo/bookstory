package com.elice.bookstore.review.domain;

import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * review domain.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  private String content;

  @Column
  private double rating;

  @Column
  private Boolean isExist;
}
