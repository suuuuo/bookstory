package com.elice.bookstore.book.domain.controller;


import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.dto.RequestQuestion;

import com.elice.bookstore.book.domain.service.QuestionService;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;
    private final UserRepository userRepository;
    /**
     * Question : Post;
     */

    @PostMapping("/v1/question")

    public ResponseEntity<Question> createQuestion(@RequestBody RequestQuestion question) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        long id = Long.parseLong(customUserDetails.getId());

        Optional<User> currentUser = userRepository.findByIdAndIsExist(id, true);

        if (currentUser.isEmpty()) {
            throw new SecurityException("사용자 인증 정보가 없습니다.");
        }
        Question newQuestion = questionService.createQuestion(question, currentUser.get());
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

    @DeleteMapping("/v1/question/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        long id = Long.parseLong(customUserDetails.getId());

        Optional<User> currentUser = userRepository.findByIdAndIsExist(id, true);

        if (currentUser.isEmpty()) {
            throw new SecurityException("사용자 인증 정보가 없습니다.");
        }

        questionService.deleteQuestion(questionId,currentUser.get());

        return ResponseEntity.ok().build();
    }

}
