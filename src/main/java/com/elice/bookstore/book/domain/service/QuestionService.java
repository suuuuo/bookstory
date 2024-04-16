package com.elice.bookstore.book.domain.service;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.mapper.QuestionMapper;
import com.elice.bookstore.book.domain.qna.QuestionStatus;
import com.elice.bookstore.book.domain.repository.BookRepository;
import com.elice.bookstore.book.domain.dto.RequestQuestion;
import com.elice.bookstore.book.domain.repository.QuestionRepository;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /**
     * Question 저장
     */
    public Question createQuestion(RequestQuestion request, User user) {

        if (user == null || Boolean.FALSE.equals(user.getIsExist())) {
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

    public List<Question> findQuestionsByBookId(Long bookId) {
        // 책의 ID를 기반으로 질문 조회
        return questionRepository.findByBookId(bookId);
    }

    /**
     * Question 삭제하기
     */

    public void deleteQuestion(Long id, User user) {

        Question question = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다."));


        if(!question.getUser().equals(user)){
            throw new IllegalArgumentException("유저가 같지 않습니다.");
        }

        questionRepository.deleteById(id);
    }

    public Question updateQuestion(RequestQuestion request, Long id, User user) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다."));

        if (!question.getUser().equals(user)) {
            throw new IllegalArgumentException("유저가 같지 않습니다.");
        }

        question.setContent(request.getContent());
        question.setStatus(QuestionStatus.ANSWER_PENDING);

        return questionRepository.save(question);
    }


}
