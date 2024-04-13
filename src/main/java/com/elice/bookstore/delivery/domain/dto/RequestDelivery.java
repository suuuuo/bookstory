package com.elice.bookstore.delivery.domain.dto;

import com.elice.bookstore.delivery.domain.Delivery;

public record RequestDelivery(
    String address,
    String receiverName,
    String receiverPhoneNumber,
    String request
) {
  public Delivery toEntity() {
    return Delivery.builder()
                   .address(address)
                   .receiverName(receiverName)
                   .receiverPhoneNumber(receiverPhoneNumber)
                   .request(request)
                   .build();
  }


}
