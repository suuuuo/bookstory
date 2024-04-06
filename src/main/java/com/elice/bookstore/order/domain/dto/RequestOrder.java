package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.order.domain.OrderStatus;
import com.elice.bookstore.user.domain.User;
import java.util.Objects;

public record RequestOrder(
    User user,
    Cart cart,
    OrderStatus orderStatus,
    int totalPrice
) {


  public RequestOrder {
    Objects.requireNonNull(user);
    Objects.requireNonNull(cart);
    Objects.requireNonNull(orderStatus);
    if (totalPrice <= 0) {
      throw new IllegalArgumentException("Total price must be greater than zero");
    }
  }

}
