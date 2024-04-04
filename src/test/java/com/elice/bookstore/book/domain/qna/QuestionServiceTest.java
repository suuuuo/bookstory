package com.elice.bookstore.book.domain.qna;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.repository.BookRepository;
import com.elice.bookstore.book.domain.dto.QuestionRequest;
import com.elice.bookstore.book.domain.repository.QuestionRepository;
import com.elice.bookstore.book.domain.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void createQuestion() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        QuestionRequest request = new QuestionRequest();
        request.setBookId(bookId);
        request.setContent("Test question content");
        request.setCreatedBy("Test user");

        Question question = request.toEntity(book);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        // When
        Question result = questionService.createQuestion(request);

        // Then
        assertThat(result.getBook().getId()).isEqualTo(bookId);
        assertThat(result.getContent()).isEqualTo(request.getContent());
        assertThat(result.getCreatedBy()).isEqualTo(request.getCreatedBy());
        assertThat(result.getStatus()).isEqualTo(QuestionStatus.ANSWER_PENDING);
    }

    @DisplayName("질문 전체 찾기 성공")
    @Test
    public void testFindAllQuestion() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        QuestionRequest request = new QuestionRequest();
        request.setBookId(bookId);
        request.setContent("Test question content");
        request.setCreatedBy("Test user");

        QuestionRequest request2 = new QuestionRequest();
        request2.setBookId(bookId);
        request2.setContent("Test question content2");
        request2.setCreatedBy("Test user2");

        Question question = request.toEntity(book);
        Question question2 = request2.toEntity(book);

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

}