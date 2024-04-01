package com.elice.bookstore.book.domain;


import com.elice.bookstore.book.domain.dto.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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



}
