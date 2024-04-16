package com.elice.bookstore.orderbook.domain.dto;

import com.elice.bookstore.orderbook.domain.OrderBook;
import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.order.domain.Order;
import com.elice.bookstore.order.domain.OrderStatus;
import com.elice.bookstore.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookDTO {
  private Long userId;
  private Long orderId;
  private Long bookId;
  private int count;
  private LocalDate orderDate;
  private OrderStatus orderStatus;
  private String itemName;

  public OrderBook toEntity() {
    User user = new User();
    user.setId(userId);

    Order order = new Order();
    order.setId(orderId);
    order.setOrderDate(orderDate);
    order.setOrderStatus(orderStatus);

    Book book = new Book();
    book.setId(bookId);
    book.setItemName(itemName);

    OrderBook orderBook = new OrderBook();
    orderBook.setUser(user);
    orderBook.setOrder(order);
    orderBook.setBook(book);
    orderBook.setCount(count);

    return orderBook;
  }
}
