package com.elice.bookstore.book.domain.service;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.RequestBook;
import com.elice.bookstore.book.domain.dto.RequestUpdateBook;
import com.elice.bookstore.book.domain.mapper.BookMapper;
import com.elice.bookstore.book.domain.repository.BookRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * BookService.
 */
@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  /**
   * create.
   */
  public Book save(RequestBook requestBook) {

    Book book = bookMapper.toEntity(requestBook);
    return bookRepository.save(book);
  }

  /**
   * read.
   */
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Book findById(Long id) {
    return bookRepository.findById(id).orElse(null);
  }

  /**
   * update.
   */
  @Transactional
  public Book update(Long id, RequestUpdateBook requestUpdateBook) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("not found:" + id));

    book.update(requestUpdateBook.getItemName(), requestUpdateBook.getPrice(),
        requestUpdateBook.getAuthor(), requestUpdateBook.getDescription(),
        requestUpdateBook.getPublisher());

    return book;
  }

  /**
   * delete.
   */
  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }


}
