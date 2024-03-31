package com.elice.bookstore.book.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailResponse {
    private String itemName;
    private String author;
    private String publisher;
    private String price;
    private String description;
}


