package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.order.domain.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RequestOrder(
    @NotNull
    Long userId,
    @NotNull
    Long cartId,
    @NotNull
    OrderStatus orderStatus,
    @Min(value = 1)
    int totalPrice
) {

}
