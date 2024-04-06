package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.order.domain.OrderStatus;
import com.elice.bookstore.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.ResponseEntity;

public record ResponseOrder(
    User user,
    Cart cart,
    OrderStatus orderStatus,
    int totalPrice
) {
  public ResponseOrder {
    Objects.requireNonNull(user);
    Objects.requireNonNull(cart);
    Objects.requireNonNull(orderStatus);
    Objects.requireNonNull(totalPrice);
  }
}
