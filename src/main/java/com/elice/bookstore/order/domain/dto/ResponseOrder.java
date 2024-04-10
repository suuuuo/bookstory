package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.order.domain.Order;
import com.elice.bookstore.order.domain.OrderStatus;
import com.elice.bookstore.user.domain.User;
import java.util.Objects;

public record ResponseOrder(
    Long userId,
    Long cartId,
    OrderStatus orderStatus,
    int totalPrice
) {
  public Order toEntity(User user, Cart cart, OrderStatus orderStatus) {
    return Order.builder()
                .user(user)
                .cart(cart)
                .orderStatus(orderStatus())
                .build();
  }


  public ResponseOrder {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(cartId);
    Objects.requireNonNull(orderStatus);
    Objects.requireNonNull(totalPrice);
  }
}
