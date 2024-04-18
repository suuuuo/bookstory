package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.order.domain.OrderStatus;
import jakarta.validation.constraints.Min;

public record RequestOrder(
    Long userId,
    Long cartId,
    OrderStatus orderStatus,
    @Min(value = 1)
    int totalPrice
) {
    public RequestOrder withUserId(Long userId) {
        return new RequestOrder(userId, cartId, orderStatus, totalPrice);
    }

}
