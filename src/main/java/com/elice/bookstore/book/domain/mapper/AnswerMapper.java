package com.elice.bookstore.book.domain.mapper;


import com.elice.bookstore.book.domain.dto.RequestAnswer;
import com.elice.bookstore.book.domain.qna.Answer;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.qna.QuestionStatus;
import com.elice.bookstore.user.domain.User;

/**
 * AnswerMapper.
 */
public class AnswerMapper {

  /**
   * Builder.
   */
  public static Answer toEntity(RequestAnswer request, Question question, User user) {
    if (request == null || question == null || user == null) {
      return null;
    }
    
    question.setStatus(QuestionStatus.ANSWERED);

    return Answer.builder()
        .question(question)
        .userRole(user.getRole())
        .createdBy("관리자")
        .content(request.getContent())
        .build();

  }
}
