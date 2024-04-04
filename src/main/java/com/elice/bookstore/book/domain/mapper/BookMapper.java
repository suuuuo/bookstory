package com.elice.bookstore.book.domain.mapper;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.BookRequest;
public class BookMapper {

    public static Book toEntity(BookRequest request) {
        if ( request == null ) {
            return null;
        }

        return Book.builder()
                .itemName(request.getItemName())
                .price(request.getPrice())
                .author(request.getAuthor())
                .description(request.getDescription())
                .publisher(request.getPublisher())
                .build();
    }
}