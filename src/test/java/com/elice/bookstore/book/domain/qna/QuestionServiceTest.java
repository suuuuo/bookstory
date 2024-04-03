package com.elice.bookstore.book.domain.qna;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    private QuestionService questionService;

    @Test
    public void testCreateQuestion() {
        // given
        Question question = new Question();
        question.setContent("Test question");
        question.setStatus(QuestionStatus.ANSWER_PENDING);
        question.setCreatedBy("tester");
        question.setCreatedAt(LocalDateTime.now());

        when(questionRepository.save(question)).thenReturn(question);

        // when
        Question createdQuestion = questionService.createQuestion(question);

        // then
        assertThat(createdQuestion).isNotNull();
        assertThat(createdQuestion.getContent()).isEqualTo("Test question");
        assertThat(createdQuestion.getStatus()).isEqualTo(QuestionStatus.ANSWER_PENDING);
        assertThat(createdQuestion.getCreatedBy()).isEqualTo("tester");
    }
}