package com.elice.bookstore.book.domain.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.repository.QuestionRepository;
import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @DisplayName("다른 유저가 삭제를 시도할 시 삭제 요청 못하게 성공")
    @Test
    void deleteQuestion_WithDifferentUser_ThrowsException() {
        // Setup
        Long questionId = 1L;
        User correctUser = new User("1", Role.USER);
        User wrongUser = new User("2", Role.USER);
        Question question = new Question();
        question.setId(questionId);
        question.setUser(correctUser);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            questionService.deleteQuestion(questionId, wrongUser);
        });

        assertEquals("유저가 같지 않습니다.", thrown.getMessage());
        verify(questionRepository, never()).deleteById(anyLong());
    }
}