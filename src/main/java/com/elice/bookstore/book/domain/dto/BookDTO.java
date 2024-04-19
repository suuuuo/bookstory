package com.elice.bookstore.book.domain.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

  private Long id;

  public BookDTO(Long id) {
    this.id = id;
  }

}
