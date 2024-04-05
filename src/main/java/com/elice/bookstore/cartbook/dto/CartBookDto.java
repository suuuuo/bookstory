package com.elice.bookstore.cartbook.dto;

import com.elice.bookstore.cartbook.domain.CartBook;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartBookDto {
  private long id;
  private long cartId;
  private long bookId;
  private String itemName;
  private int price;
  private String imgPath;
  private int count;

  public CartBookDto(CartBook cartBook) {
    this.id = cartBook.getId();
    this.cartId = cartBook.getCart().getId();
    this.bookId = cartBook.getBook().getId();
    this.itemName = cartBook.getBook().getItemName();
    this.price = cartBook.getBook().getPrice();
    this.imgPath = cartBook.getBook().getImgPath();
    this.count = cartBook.getCount();
  }
}

