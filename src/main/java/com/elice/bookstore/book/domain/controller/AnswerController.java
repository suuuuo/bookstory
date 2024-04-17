package com.elice.bookstore.book.domain.controller;

import com.elice.bookstore.book.domain.dto.RequestAnswer;
import com.elice.bookstore.book.domain.qna.Answer;
import com.elice.bookstore.book.domain.service.AnswerService;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AnswerController.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnswerController {

  private final AnswerService answerService;
  private final UserRepository userRepository;

  /**
   * Post Answer.
   */
  @PostMapping("/v1/answer")
  public ResponseEntity<Answer> addAnswer(@RequestBody RequestAnswer answer) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    long id = Long.parseLong(customUserDetails.getId());

    Optional<User> currentUser = userRepository.findByIdAndIsExist(id, true);

    if (currentUser.isEmpty()) {
      throw new SecurityException("사용자 인증 정보가 없습니다.");
    }

    Answer savedAnswer = answerService.saveAnswer(answer, currentUser.get());
    return ResponseEntity.ok(savedAnswer);
  }

  @GetMapping("/v1/answer/{id}")
  public ResponseEntity<Answer> readAnswer(@PathVariable Long id) {
    Answer readAnswer = answerService.redadAnswer(id);
    return ResponseEntity.ok(readAnswer);
  }


}
