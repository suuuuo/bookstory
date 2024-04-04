package com.elice.bookstore.book.domain.service;


import com.elice.bookstore.book.domain.qna.Answer;
import com.elice.bookstore.book.domain.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }
}
