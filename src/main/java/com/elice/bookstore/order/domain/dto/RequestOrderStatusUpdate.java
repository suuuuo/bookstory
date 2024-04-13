package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.order.domain.OrderStatus;

public record RequestOrderStatusUpdate(Long id, OrderStatus orderStatus) {

}
