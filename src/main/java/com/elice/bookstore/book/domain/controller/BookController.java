package com.elice.bookstore.book.domain.controller;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.BookRequest;
import com.elice.bookstore.book.domain.dto.BookResponse;
import com.elice.bookstore.book.domain.dto.UpdateBookRequest;
import com.elice.bookstore.book.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * Post
     */
    @PostMapping("/api/books")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        Book saveBook = bookService.save(bookRequest);
        BookResponse bookResponse = new BookResponse(saveBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }
    /**
     * Get
     */
    @GetMapping("/api/books")
    public ResponseEntity<List<BookResponse>> findAllBooks() {
        List<BookResponse> books = bookService.findAll()
                .stream()
                .map(BookResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(books);

    }
    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookResponse> findByIdBooks(@PathVariable Long id) {
        Book book = bookService.findById(id);

        return ResponseEntity.ok()
                .body(new BookResponse(book));
    }
    /**
     * Put
     */
    @PutMapping("/api/books/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest updateBookRequest) {
        Book updatedBook = bookService.update(id, updateBookRequest);
        BookResponse bookResponse = new BookResponse(updatedBook);

        return ResponseEntity.ok()
                .body(bookResponse);
    }

    /**
     * Delete
     */
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return ResponseEntity.ok().build();
    }

}
