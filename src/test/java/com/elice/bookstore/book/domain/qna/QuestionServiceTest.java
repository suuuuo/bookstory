package com.elice.bookstore.book.domain.qna;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.BookRepository;
import com.elice.bookstore.book.domain.qna.dto.QuestionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

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
}