package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.order.domain.Order;
import com.elice.bookstore.order.domain.OrderStatus;
import com.elice.bookstore.user.domain.User;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

public record ResponseOrder(
    Long userId,
    Long cartId,
    OrderStatus orderStatus,
    @Min(value = 1)
    int totalPrice,
    LocalDate orderDate,
    LocalDate paymentDate
) {
  public Order toEntity(User user, Cart cart) {
    return Order.builder()
                .user(user)
                .cart(cart)
                .orderStatus(orderStatus())
                .totalPrice(totalPrice)
                .orderDate(orderDate)
                .paymentDate(paymentDate)
                .build();
  }
}
