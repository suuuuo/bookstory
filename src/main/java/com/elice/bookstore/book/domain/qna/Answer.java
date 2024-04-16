package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.config.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

/**
 * Answer.
 */
@Entity
@NoArgsConstructor
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
