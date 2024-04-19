package com.elice.bookstore.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseBookCategory {
  private Long bookId;
  private Long categoryId1;
  private Long categoryId2;
  private Long categoryId3;

}
