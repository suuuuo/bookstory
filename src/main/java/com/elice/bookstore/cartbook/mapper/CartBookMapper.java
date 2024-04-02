package com.elice.bookstore.cartbook.mapper;


import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.dto.CartBookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class CartBookMapper {
  public static CartBookDto ToDto(CartBook cartBook) {
    CartBookDto cartBookDto = new CartBookDto();
    cartBookDto.setId(cartBook.getId());
    cartBookDto.setCartId(cartBook.getCart().getId());
    cartBookDto.setBookId(cartBook.getBook().getId());
    cartBookDto.setItemName(cartBook.getBook().getItemName());
    cartBookDto.setPrice(cartBook.getBook().getPrice());
    cartBookDto.setImgPath(cartBook.getBook().getImgPath());
    cartBookDto.setCount(cartBook.getCount());

    return cartBookDto;
  }
}

