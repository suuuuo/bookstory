package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.config.audit.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private String content;


}
