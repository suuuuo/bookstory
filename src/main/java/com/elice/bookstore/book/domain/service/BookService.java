package com.elice.bookstore.book.domain.service;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.mapper.BookMapper;
import com.elice.bookstore.book.domain.dto.BookRequest;
import com.elice.bookstore.book.domain.dto.UpdateBookRequest;
import com.elice.bookstore.book.domain.repository.BookRepository;
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

        Book book = BookMapper.toEntity(bookRequest);
        return bookRepository.save(book);
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
