package com.elice.bookstore.cartbook.Mapper;

import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.dto.ResponseCartBook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartBookMapper {
  default ResponseCartBook CartBookToResponseCartBook(CartBook cartBook){
    return new ResponseCartBook(
        cartBook.getId(),
        cartBook.getCart().getId(),
        cartBook.getBook().getItemName(),
        cartBook.getBook().getPrice(),
        cartBook.getBook().getImgPath(),
        cartBook.getCount()
    );
  }
}

