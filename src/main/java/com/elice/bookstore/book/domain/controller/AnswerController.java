package com.elice.bookstore.book.domain.controller;
import com.elice.bookstore.book.domain.qna.Answer;
import com.elice.bookstore.book.domain.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<Answer> addAnswer(@RequestBody Answer answer) {
        Answer savedAnswer = answerService.saveAnswer(answer);
        return ResponseEntity.ok(savedAnswer);
    }
}
