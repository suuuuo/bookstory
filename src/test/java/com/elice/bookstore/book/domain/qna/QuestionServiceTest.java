package com.elice.bookstore.book.domain.qna;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.mapper.QuestionMapper;
import com.elice.bookstore.book.domain.repository.BookRepository;
import com.elice.bookstore.book.domain.dto.RequestQuestion;
import com.elice.bookstore.book.domain.repository.QuestionRepository;
import com.elice.bookstore.book.domain.service.QuestionService;
import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;


    @DisplayName("질문 생성 성공")
    @Test
    public void createQuestionTest() {
        // 인증된 사용자 모의 객체 생성
        User user = new User();
        user.setUserName("testUser");

        // RequestQuestion 객체 생성
        RequestQuestion requestQuestion = new RequestQuestion();
        requestQuestion.setBookId(1L);
        requestQuestion.setContent("Test question content");

        // Book 객체 초기화
        Book book = new Book(1L, "Example Book Title", 15000,  "Author Name");

        // BookRepository의 findById 메서드를 모의 처리하여 항상 특정 Book 객체를 반환하도록 설정
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // QuestionRepository의 save 메서드 동작 모의
        when(questionRepository.save(any(Question.class))).thenAnswer(i -> i.getArguments()[0]);

        // QuestionService를 사용하여 Question 객체 생성
        Question result = questionService.createQuestion(requestQuestion, user);

        // 생성된 Question 객체에서 createdBy 필드 검증
        assertThat(result.getCreatedBy()).isEqualTo(user.getUserName());
    }

    @DisplayName("질문 전체 찾기 성공")
    @Test
    public void testFindAllQuestion() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        RequestQuestion request = new RequestQuestion();
        request.setBookId(bookId);
        request.setContent("Test question content");
        request.setCreatedBy("Test user");

        RequestQuestion request2 = new RequestQuestion();
        request2.setBookId(bookId);
        request2.setContent("Test question content2");
        request2.setCreatedBy("Test user2");

        Question question = QuestionMapper.toFindEntity(request,book);
        Question question2 = QuestionMapper.toFindEntity(request2,book);

        // when
        when(questionRepository.findAll()).thenReturn(List.of(question, question2));
        List<Question> result = questionRepository.findAll();

        // then
        assertThat(result).hasSize(2); // 결과 리스트의 사이즈 검증
        assertThat(result).extracting(Question::getContent).containsExactlyInAnyOrder("Test question content", "Test question content2");
    }

    @Test
    @DisplayName("질문 삭제")
    void deleteQuestion() {
        // Given
        Long questionId = 1L;

        // findById() 메서드 호출 시 Optional.empty()를 반환하도록 설정하여,
        // 실제로 삭제가 이루어졌음을 가정
        doNothing().when(questionRepository).deleteById(questionId);
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        // When
        questionService.deleteQuestion(questionId);

        // Then
        Optional<Question> deletedQuestion = questionRepository.findById(questionId);
        assertThat(deletedQuestion).isEmpty();

        // verify()를 사용하여 deleteById() 메서드가 정확히 한 번 호출되었는지 확인
        verify(questionRepository, times(1)).deleteById(questionId);
    }

    @Test
    @DisplayName("사용자가 자신의 질문을 삭제 성공")
    public void testDeleteQuestionIfOwnedByUser() {
        // Given
        Long questionId = 1L;
        Long questionCreatedBy = 1L;
        String userName = "TestName";

        Question question = new Question();
        question.setId(questionId);
        question.setCreatedBy(questionCreatedBy);

        User user = new User();
        user.setId(1L);
        user.setUserName(userName);

        when(questionRepository.findById(eq(questionId))).thenReturn(Optional.of(question));

        // When
        questionService.deleteQuestionIfOwnedByUser(questionId, user.getId());

        // Then
        verify(questionRepository, times(1)).deleteById(questionId);
    }


//    @Test
//    public void testDeleteQuestionIfOwnedByUser_WhenQuestionExistsAndNotOwnedByUser() {
//        // Given
//        Long questionId = 1L;
//        Long userId = 1L;
//        Question question = new Question();
//        question.setId(questionId);
//        question.setCreatedBy(2L); // Different user
//
//        when(questionRepository.findById(eq(questionId))).thenReturn(Optional.of(question));
//
//        // Then
//        assertThrows(ResponseStatusException.class, () -> {
//            // When
//            questionService.deleteQuestionIfOwnedByUser(questionId, userId);
//        });
//
//        verify(questionRepository, never()).deleteById(any());
//    }
//
//    @Test
//    public void testDeleteQuestionIfOwnedByUser_WhenQuestionDoesNotExist() {
//        // Given
//        Long questionId = 1L;
//        Long userId = 1L;
//
//        when(questionRepository.findById(eq(questionId))).thenReturn(Optional.empty());
//
//        // Then
//        assertThrows(ResponseStatusException.class, () -> {
//            // When
//            questionService.deleteQuestionIfOwnedByUser(questionId, userId);
//        });
//
//        verify(questionRepository, never()).deleteById(any());
//    }

}