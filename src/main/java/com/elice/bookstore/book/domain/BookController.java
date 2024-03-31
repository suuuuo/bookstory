package com.elice.bookstore.book.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;


    // 생성을 어떻게 할 것인가 .. ?
    // @PostMapping("/api/books")


    // 전체 불러오기
    @GetMapping("/api/books")
    public ResponseEntity<List<BookResponse>> findAllBooks(){
        List<BookResponse> books = bookService.findAll()
                .stream()
                .map(BookResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(books);

    }


}
