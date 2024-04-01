package com.elice.bookstore.book.domain;


import com.elice.bookstore.book.domain.dto.BookRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * Post
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> addBook(@RequestBody BookRequest bookRequest) {
        Book saveBook = bookService.save(bookRequest);


        return ResponseEntity.status(HttpStatus.CREATED).body(saveBook);
    }
}
