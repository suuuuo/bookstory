package com.elice.bookstore.book.domain.qna;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.user.domain.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Question.
 */
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

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Answer> answers = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private QuestionStatus status;
  private String title;
  private String content;
  private String createdBy;
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;

  /**
   * Builder.
   */
  @Builder
  public Question(Book book, User user, String title, QuestionStatus status, String content,
      String createdBy) {
    this.book = book;
    this.user = user;
    this.title = title;
    this.status = status;
    this.content = content;
    this.createdBy = createdBy;
    this.createdAt = LocalDateTime.now();
  }

}
