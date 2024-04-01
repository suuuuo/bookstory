package com.elice.bookstore.book.domain;


import com.elice.bookstore.book.domain.dto.BookRequest;
import com.elice.bookstore.book.domain.dto.BookResponse;
import com.elice.bookstore.book.domain.dto.UpdateBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;


/**
 * BookController
 */
@RequiredArgsConstructor
@RestController
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

  /**
   * Put
   */
  @PutMapping("/api/books/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest updateBookRequest) {
    Book updateBook = bookService.update(id, updateBookRequest);

    return ResponseEntity.ok()
        .body(updateBook);
  }


  /**
   * 책 하나 불러오기
   */
  @GetMapping("/api/books/{id}")
  public ResponseEntity<BookResponse> findByIdBooks(@PathVariable Long id) {
    Book book = bookService.findById(id);

    return ResponseEntity.ok()
        .body(new BookResponse(book));
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
