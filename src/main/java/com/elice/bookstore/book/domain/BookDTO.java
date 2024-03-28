package com.elice.bookstore.book.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // 모든 필드 값을 받는 생성자 추가
public class BookDTO {
    private String title;
    private String author;
    private String publisher;
}


