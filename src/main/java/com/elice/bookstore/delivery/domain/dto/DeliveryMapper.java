package com.elice.bookstore.delivery.domain.dto;

import com.elice.bookstore.delivery.domain.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface DeliveryMapper {

  // requestDelivery -> delivery
  @Mapping(source = "orderId", target = "order.id")
  Delivery requestDeliveryToDelivery(RequestDelivery requestDelivery);

  @Mapping(source = "order.id", target = "orderId")
  ResponseDelivery toResponseDelivery(Delivery delivery);

}
