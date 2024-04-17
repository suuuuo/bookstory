package com.elice.bookstore.book.domain.controller;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.RequestBook;
import com.elice.bookstore.book.domain.dto.RequestUpdateBook;
import com.elice.bookstore.book.domain.dto.ResponseBook;
import com.elice.bookstore.book.domain.service.BookService;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookController.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

  private final BookService bookService;
  private final UserRepository userRepository;

  /**
   * Post book.
   *
   * @param requestBook .
   * @return ResponseBook .
   */
  @PostMapping("/v1//books/save")
  public ResponseEntity<ResponseBook> addBook(@RequestBody RequestBook requestBook) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    long id = Long.parseLong(customUserDetails.getId());

    Optional<User> currentUser = userRepository.findByIdAndIsExist(id, true);

    if (currentUser.isEmpty()) {
      throw new SecurityException("사용자 인증 정보가 없습니다.");
    }

    Book saveBook = bookService.save(requestBook);
    ResponseBook responseBook = new ResponseBook(saveBook);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseBook);
  }

  /**
   * Get book.
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

  /**
   * Get book.
   *
   * @param id bookId .
   * @return ResponseBook .
   */
  @GetMapping("/v1/books/{id}")
  public ResponseEntity<ResponseBook> findByIdBooks(@PathVariable Long id) {
    Book book = bookService.findById(id);

    return ResponseEntity.ok()
        .body(new ResponseBook(book));
  }

  @GetMapping("v1/books/title")
  public ResponseEntity<Book> getBookInfo(@RequestParam("title") String title) {
    Book book = bookService.getBookInfo(title);
    if (book != null) {
      return ResponseEntity.ok(book);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Put .
   */
  @PutMapping("/v1/books/{id}")
  public ResponseEntity<ResponseBook> updateBook(@PathVariable Long id,
      @RequestBody RequestUpdateBook requestUpdateBook) {
    Book updatedBook = bookService.update(id, requestUpdateBook);
    ResponseBook responseBook = new ResponseBook(updatedBook);

    return ResponseEntity.ok()
        .body(responseBook);
  }

  /**
   * Delete .
   */
  @DeleteMapping("/v1/books/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);

    return ResponseEntity.ok().build();
  }

}
