package com.elice.bookstore.book.domain.service;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.mapper.QuestionMapper;
import com.elice.bookstore.book.domain.repository.BookRepository;
import com.elice.bookstore.book.domain.dto.QuestionRequest;
import com.elice.bookstore.book.domain.repository.QuestionRepository;
import com.elice.bookstore.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final BookRepository bookRepository;

    /**
     * Question 저장
     */
    public Question createQuestion(QuestionRequest request, User user) {

        if(user == null || Boolean.FALSE.equals(user.getIsExist())){
            throw new IllegalArgumentException("유저가 없거나 생성되지 않았습니다.");
        }

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + request.getBookId()));

        // Question 객체 생성
        Question question = QuestionMapper.toEntity(request, book, user);

        // Question 객체 저장 및 반환
        return questionRepository.save(question);
    }

    /**
     * Question 불러오기
     */
    public List<Question> findAllQuestion() {
        return questionRepository.findAll();
    }

    /**
     * Question 삭제하기
     */
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

}
