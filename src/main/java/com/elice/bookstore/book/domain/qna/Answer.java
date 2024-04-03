package com.elice.bookstore.book.domain.qna;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String content;
    private String createdBy;
    private LocalDateTime localDateTime;

}
