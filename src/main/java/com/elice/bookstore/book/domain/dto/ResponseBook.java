package com.elice.bookstore.book.domain.dto;

import com.elice.bookstore.book.domain.Book;
import lombok.Getter;

/**
 * ResponseBook.
 */
@Getter
public class ResponseBook {

  private final String itemName;
  private final Integer price;
  private final String author;
  private final String publisher;
  private final String description;
  private final Integer stock;

  public ResponseBook(Book book) {
    this.itemName = book.getItemName();
    this.price = book.getPrice();
    this.author = book.getAuthor();
    this.publisher = book.getPublisher();
    this.description = book.getDescription();
    this.stock = book.getStock();
  }

}
