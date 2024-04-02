package com.elice.bookstore.cartbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartBookDto {
  private long id;
  private long cartId;
  private long bookId;
  private String itemName;
  private int price;
  private String imgPath;
  private int count;

  public CartBookDto() {

  }
}

