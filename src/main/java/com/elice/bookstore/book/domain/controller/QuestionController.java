package com.elice.bookstore.book.domain.controller;


import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.dto.RequestQuestion;

import com.elice.bookstore.book.domain.service.QuestionService;
import com.elice.bookstore.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;

    /**
     * Question : Post;
     */

    @PostMapping("/v1/question")
    public ResponseEntity<Question> createQuestion(@RequestBody RequestQuestion question, @AuthenticationPrincipal User currentUser) {
        Question newQuestion = questionService.createQuestion(question, currentUser);
        return ResponseEntity.ok(newQuestion);
    }


    /**
     * Question : Get;
     */
    @GetMapping("/v1/question")
    public ResponseEntity<List<Question>> findALlQuestion() {
        List<Question> questions = questionService.findAllQuestion();

        return ResponseEntity.ok(questions);
    }

    @GetMapping("/v1/question/{id}")
    public ResponseEntity<List<Question>> findQuestionsByBookId(@PathVariable Long id) {
        List<Question> questions = questionService.findQuestionsByBookId(id);
        return ResponseEntity.ok(questions);
    }

    /**
     * Question : Delete;
     */

    @DeleteMapping("/v1/question/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        questionService.deleteQuestionIfOwnedByUser(id, currentUser.getId());
        return ResponseEntity.ok().build();
    }

}
