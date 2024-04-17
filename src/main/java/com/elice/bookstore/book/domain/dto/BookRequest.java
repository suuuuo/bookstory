package com.elice.bookstore.book.domain.dto;


import com.elice.bookstore.book.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * bookRequest.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

  private String itemName;
  private Integer price;
  private String author;
  private String description;
  private String publisher;


  public Book toEntity() {
    return Book.builder()
        .itemName(itemName)
        .price(price)
        .author(author)
        .description(description)
        .publisher(publisher)
        .build();
  }

}
