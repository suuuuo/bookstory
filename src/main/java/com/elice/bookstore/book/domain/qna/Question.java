package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.book.domain.Book;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String content;
    private String createdBy;
    private LocalDateTime createdAt;

}
