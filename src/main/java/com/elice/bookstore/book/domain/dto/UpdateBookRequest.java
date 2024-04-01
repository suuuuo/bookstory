package com.elice.bookstore.book.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * updateBookRequest.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateBookRequest {
  private String itemName;
  private Integer price;
  private String author;
  private String description;
  private String publisher;
}
