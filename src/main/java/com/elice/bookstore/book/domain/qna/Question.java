package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.user.domain.User;
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
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Question(Book book, User user, String title, QuestionStatus status, String content, String createdBy) {
        this.book = book;
        this.user = user;
        this.title = title;
        this.status = status;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

}
