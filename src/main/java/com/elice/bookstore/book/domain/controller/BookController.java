package com.elice.bookstore.book.domain.controller;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.RequestBook;
import com.elice.bookstore.book.domain.dto.ResponseBook;
import com.elice.bookstore.book.domain.dto.RequestUpdateBook;
import com.elice.bookstore.book.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    /**
     * Post
     */
    @PostMapping("/v1/books")
    public ResponseEntity<ResponseBook> addBook(@RequestBody RequestBook requestBook) {
        Book saveBook = bookService.save(requestBook);
        ResponseBook responseBook = new ResponseBook(saveBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBook);
    }
    /**
     * Get
     */
    @GetMapping("/v1/books")
    public ResponseEntity<List<ResponseBook>> findAllBooks() {
        List<ResponseBook> books = bookService.findAll()
                .stream()
                .map(ResponseBook::new)
                .toList();

        return ResponseEntity.ok()
                .body(books);

    }
    @GetMapping("/v1/books/{id}")
    public ResponseEntity<ResponseBook> findByIdBooks(@PathVariable Long id) {
        Book book = bookService.findById(id);

        return ResponseEntity.ok()
                .body(new ResponseBook(book));
    }
    /**
     * Put
     */
    @PutMapping("/v1/books/{id}")
    public ResponseEntity<ResponseBook> updateBook(@PathVariable Long id, @RequestBody RequestUpdateBook requestUpdateBook) {
        Book updatedBook = bookService.update(id, requestUpdateBook);
        ResponseBook responseBook = new ResponseBook(updatedBook);

        return ResponseEntity.ok()
                .body(responseBook);
    }

    /**
     * Delete
     */
    @DeleteMapping("/v1/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return ResponseEntity.ok().build();
    }

}
