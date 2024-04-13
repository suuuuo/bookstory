package com.elice.bookstore.book.domain.controller;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.service.BookService;
import com.elice.bookstore.book.domain.dto.BookDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/best-sellers")
    public Flux<BookDetailResponse> getBestSellers() {
        return bookService.fetchBestSellers();
    }

    @GetMapping("/save-best-sellers")
    public Flux<Book> fetchAndSave(){
        return bookService.fetchAndSaveBestSellers();
    }

    @GetMapping("/")
    public List<Book> findAllBook(){
        return bookService.findAll();
    }



}
