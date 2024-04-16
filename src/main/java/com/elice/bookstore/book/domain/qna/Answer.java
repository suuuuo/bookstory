package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.config.audit.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Answer.
 */
@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Answer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id")
  @JsonBackReference
  private Question question;

  @Column
  private String createdBy;


  @Enumerated(EnumType.STRING)
  @Column
  private QuestionStatus status;

  @Column(nullable = false)
  private String content;


}
