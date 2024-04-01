package com.elice.bookstore.book.domain;


import com.elice.bookstore.book.domain.dto.BookRequest;
import com.elice.bookstore.book.domain.dto.UpdateBookRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * create
     */
    public Book save(BookRequest bookRequest) {
        return bookRepository.save(bookRequest.toEntity());
    }

    /**
     * read
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    /**
     * update
     */
    @Transactional
    public Book update(Long id, UpdateBookRequest updateBookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found:" + id));

        book.update(updateBookRequest.getItemName(), updateBookRequest.getPrice(),
                updateBookRequest.getAuthor(), updateBookRequest.getDescription(), updateBookRequest.getPublisher());

        return book;
    }

    /**
     * delete
     */
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }



}
