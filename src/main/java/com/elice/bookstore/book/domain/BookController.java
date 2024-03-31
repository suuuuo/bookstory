package com.elice.bookstore.book.domain;


import com.elice.bookstore.book.domain.dto.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 특정 책 불러오기
    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookResponse> findByIdBooks(@PathVariable Long id){
        Book book = bookService.findById(id);

        return ResponseEntity.ok()
                .body(new BookResponse(book));
    }

    // 삭제
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);

        return ResponseEntity.ok().build();
    }
}
