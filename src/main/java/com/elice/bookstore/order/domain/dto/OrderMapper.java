package com.elice.bookstore.order.domain.dto;

import com.elice.bookstore.order.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

  // requsetOrder -> order
  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "cartId", target = "cart.id")
  Order requestOrderToOrder(RequestOrder requestOrder);

  // order -> responseOrder
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "cart.id", target = "cartId")
  ResponseOrder toResponseOrder(Order order);

}
