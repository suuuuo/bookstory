package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.order.domain.OrderStatus;
import java.util.Objects;

public record RequestOrder(
    Long userId,
    Long cartId,
    OrderStatus orderStatus,
    int totalPrice
) {

  public RequestOrder {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(cartId);
    Objects.requireNonNull(orderStatus);
    if (totalPrice <= 0) {
      throw new IllegalArgumentException("Total price must be greater than zero");
    }
  }

}
