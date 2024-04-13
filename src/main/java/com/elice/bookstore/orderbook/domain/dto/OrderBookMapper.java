package com.elice.bookstore.orderbook.domain.dto;

import com.elice.bookstore.orderbook.domain.OrderBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderBookMapper {

  // orderBook -> orderDto
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "book.id", target = "bookId")
  OrderBookDTO toOrderBookDTO(OrderBook orderBook);

}
