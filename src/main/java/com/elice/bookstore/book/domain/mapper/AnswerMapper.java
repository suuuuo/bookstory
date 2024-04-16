package com.elice.bookstore.book.domain.mapper;


import com.elice.bookstore.book.domain.dto.RequestAnswer;
import com.elice.bookstore.book.domain.qna.Answer;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.qna.QuestionStatus;

/**
 * AnswerMapper.
 */
public class AnswerMapper {

  /**
   * Builder.
   */
  public static Answer toEntity(RequestAnswer request, Question question) {
    if (request == null || question == null) {
      return null;
    }

    question.setStatus(QuestionStatus.ANSWERED);

    return Answer.builder()
        .question(question)
        .createdBy("관리자")
        .content(request.getContent())
        .build();


  }
}
