package com.elice.bookstore.delivery.domain.dto;

import com.elice.bookstore.delivery.domain.Delivery;
import com.elice.bookstore.order.domain.Order;

public record ResponseDelivery(
    Long orderId,
    String address,
    String receiverName,
    String receiverPhoneNumber,
    String request
) {
  public Delivery toEntity(Order order) {
    return Delivery.builder()
        .order(order)
        .address(address)
        .receiverName(receiverName)
        .receiverPhoneNumber(receiverPhoneNumber)
        .request(request)
        .build();
  }


}
