package com.elice.bookstore.category.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RequestBookList {
  private Long bookId;
  private String itemName;
  private Integer price;
  private String author;
  private String description;
  private String publisher;
  private List<String> category;
  private String isbn;
}
