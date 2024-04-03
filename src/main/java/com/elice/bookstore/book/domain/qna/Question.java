package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.book.domain.Book;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private QuestionStatus status;
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;

    @Builder
    public Question(Book book, QuestionStatus status, String content, String createdBy) {
        this.book = book;
        this.status = status;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

}
