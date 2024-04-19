package com.elice.bookstore.book.domain.dto;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.qna.QuestionStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuestionDTO {

  private Long id;
  private BookDTO book;
  private String title;
  private String content;
  private String createdBy;
  private QuestionStatus status;
  private LocalDateTime createdAt;


  // 생성자
  public QuestionDTO(Long id, Book book, String title, String content, String createdBy,
      LocalDateTime createdAt, QuestionStatus status) {
    this.id = id;
    this.book = new BookDTO(book.getId());  // Book 정보 중 ID만 사용
    this.title = title;
    this.content = content;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.status = status;
  }


}
