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


}