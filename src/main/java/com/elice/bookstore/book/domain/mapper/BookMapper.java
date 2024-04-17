package com.elice.bookstore.book.domain.mapper;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.BookSellStatus;
import com.elice.bookstore.book.domain.dto.RequestBook;
import com.elice.bookstore.user.domain.User;


/**
 * BookMapper.
 */
public class BookMapper {

  public static Book toEntity(RequestBook request, User user) {
    if (request == null || user == null) {
      return null;
    }

    return Book.builder()
        .itemName(request.getItemName())
        .price(request.getPrice())
        .author(request.getAuthor())
        .description(request.getDescription())
        .publisher(request.getPublisher())
        .sellCount(0)
        .stock(0)
        .status(BookSellStatus.AVAILABLE)
        .build();
  }


}
