package com.elice.bookstore.book.domain.dto;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.qna.QuestionStatus;
import com.elice.bookstore.user.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * ResponseBook.
 */
@Getter
public class ResponseQuestion {

  private final Book book;
  private final User user;
  private final String title;
  private final QuestionStatus status;
  private final String content;
  private final String createdBy;
  private final LocalDateTime createdAt;

  public ResponseQuestion(Book book, User user, String title, QuestionStatus status, String content,
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

