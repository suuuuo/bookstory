package com.elice.bookstore.order.domain;

import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * order domain.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  private LocalDate orderDate;

  @Column
  private String orderNumber;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Column
  private LocalDate paymentDate;

  @Column
  private int totalPrice;

  @Column
  private Boolean isExist;
}
