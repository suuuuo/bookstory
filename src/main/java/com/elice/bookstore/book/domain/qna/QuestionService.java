package com.elice.bookstore.book.domain.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * Question 저장
     */
    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }

    /**
     * Question 불러오기
     */
    public List<Question> findAllQuestion(){
        return questionRepository.findAll();
    }


}
