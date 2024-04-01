package com.elice.bookstore.delivery.domain;

import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.config.audit.BaseTimeEntity;
import com.elice.bookstore.order.domain.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

/**
 * delivery domain.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @Column
  private String address;

  @Column
  private String deliveryStatus;

  @Column
  private String receiverName;

  @Column
  private String receiverPhoneNumber;


  private String request;

  @Column
  @CreationTimestamp
  private LocalDate deliveredDate;

  @ColumnDefault("0")
  private Boolean isExist;
}
