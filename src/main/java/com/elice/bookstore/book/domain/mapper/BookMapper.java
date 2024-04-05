package com.elice.bookstore.book.domain.mapper;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.RequestBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "itemName", source = "itemName")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "publisher", source = "publisher")
    Book toEntity(RequestBook request);
}