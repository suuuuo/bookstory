package com.elice.bookstore.book.domain.service;


import com.elice.bookstore.book.domain.dto.RequestAnswer;
import com.elice.bookstore.book.domain.mapper.AnswerMapper;
import com.elice.bookstore.book.domain.qna.Answer;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.repository.AnswerRepository;
import com.elice.bookstore.book.domain.repository.QuestionRepository;
import com.elice.bookstore.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AnswerService.
 */
@Service
@RequiredArgsConstructor
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;

  public Answer saveAnswer(RequestAnswer request, User user) {

    if (user == null || Boolean.FALSE.equals(user.getIsExist())) {
      throw new IllegalArgumentException("유저가 없거나 생성되지 않았습니다.");
    }
    Question question = questionRepository.findById(request.getQuestionId())
        .orElseThrow(
            () -> new IllegalArgumentException("Question not found: " + request.getQuestionId()));

    Answer answer = AnswerMapper.toEntity(request, question, user);

    return answerRepository.save(answer);
  }

  public Answer redadAnswer(Long id) {
    return answerRepository.findByQuestionId(id);
  }


}
