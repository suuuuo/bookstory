package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.config.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();


    private QuestionStatus status;
    private String content;
    private Long createdBy;
    private LocalDateTime createdAt;


    @Builder
    public Question(Book book, QuestionStatus status, String content, Long createdBy) {
        this.book = book;
        this.status = status;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

}
