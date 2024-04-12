package com.elice.bookstore.orderbook.domain.dto;

import com.elice.bookstore.orderbook.domain.OrderBook;
import com.elice.bookstore.user.domain.User;

public record RequestOrderBook(
    // 필요한 필드'만' 작성한다. 필드를 인자로 받아서 생성하는 생성자가 자동으로 생성됨.
    Long userId
) {
  // 필요한 메서드를 작성한다. toEntity , Null 체크 등.
  public OrderBook toEntity(User user) {
    return OrderBook.builder()
                    .user(user)
                    .build();
  }

}
