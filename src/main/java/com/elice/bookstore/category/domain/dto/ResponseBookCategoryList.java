package com.elice.bookstore.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseBookCategoryList {

  private Long bookId;
  private Long categoryLevel1;
  private Long categoryLevel2;
  private Long categoryLevel3;

}
