package com.elice.bookstore.delivery.domain.dto;

import com.elice.bookstore.delivery.domain.Delivery;
import com.elice.bookstore.order.domain.Order;

public record RequestDelivery(
    Long orderId,
    String address,
    String receiverName,
    String receiverPhoneNumber,
    String request
) {



}
