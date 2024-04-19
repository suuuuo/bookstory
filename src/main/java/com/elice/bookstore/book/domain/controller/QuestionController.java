package com.elice.bookstore.book.domain.controller;


import com.elice.bookstore.book.domain.dto.QuestionDTO;
import com.elice.bookstore.book.domain.dto.RequestQuestion;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.service.QuestionService;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * QuestionController.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {

  private final QuestionService questionService;
  private final UserRepository userRepository;

  /**
   * Post question.
   *
   * @param question .
   * @return Question .
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
   * Get question.
   */
  @GetMapping("/v1/question")
  public ResponseEntity<List<Question>> findALlQuestion() {
    List<Question> questions = questionService.findAllQuestion();

    return ResponseEntity.ok(questions);
  }

  @GetMapping("/v1/question/{id}")
  public ResponseEntity<List<QuestionDTO>> findQuestionsByBookId(@PathVariable Long id) {
    List<Question> questions = questionService.findQuestionsByBookId(id);
    List<QuestionDTO> questionDTOs = questions.stream()
        .map(question -> new QuestionDTO(
            question.getId(),
            question.getBook(),
            question.getTitle(),
            question.getContent(),
            question.getCreatedBy(),
            question.getCreatedAt()))
        .collect(Collectors.toList());
    return ResponseEntity.ok(questionDTOs);
  }

  /**
   * Question : Delete .
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

    questionService.deleteQuestion(questionId, currentUser.get());

    return ResponseEntity.ok().build();
  }

  /**
   * Put question.
   *
   * @param questionId .
   * @return Question .
   */

  @PutMapping("/v1/question/{questionId}")
  public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId,
      @RequestBody RequestQuestion question) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    long id = Long.parseLong(customUserDetails.getId());

    Optional<User> currentUser = userRepository.findByIdAndIsExist(id, true);

    if (currentUser.isEmpty()) {
      throw new SecurityException("사용자 인증 정보가 없습니다.");
    }

    questionService.updateQuestion(question, questionId, currentUser.get());

    return ResponseEntity.ok().build();
  }


}
