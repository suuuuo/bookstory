package com.elice.bookstore.book.domain.qna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.List;

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



        // when
        when(questionRepository.save(question)).thenReturn(question);
        Question createdQuestion = questionService.createQuestion(question);

        // then
        assertThat(createdQuestion).isNotNull();
        assertThat(createdQuestion.getContent()).isEqualTo("Test question");
        assertThat(createdQuestion.getStatus()).isEqualTo(QuestionStatus.ANSWER_PENDING);
        assertThat(createdQuestion.getCreatedBy()).isEqualTo("tester");
    }

    @Test
    public void testFindAllQuestion(){
        // given
        Question question = new Question();
        question.setContent("Test question");
        question.setStatus(QuestionStatus.ANSWER_PENDING);
        question.setCreatedBy("tester");
        question.setCreatedAt(LocalDateTime.now());

        Question question2 = new Question();
        question2.setContent("Test question2");
        question2.setStatus(QuestionStatus.ANSWER_PENDING);
        question2.setCreatedBy("tester2");
        question2.setCreatedAt(LocalDateTime.now());

        questionService.createQuestion(question);
        questionService.createQuestion(question2);



        // when
        when(questionRepository.findAll()).thenReturn(List.of(question, question2));
        List<Question> result = questionRepository.findAll();

        // then
        assertThat(result).hasSize(2); // 결과 리스트의 사이즈 검증
        assertThat(result).extracting(Question::getContent).containsExactlyInAnyOrder("Test question", "Test question2");

    }

}