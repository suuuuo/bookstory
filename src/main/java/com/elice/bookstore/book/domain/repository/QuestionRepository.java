package com.elice.bookstore.book.domain.repository;

import com.elice.bookstore.book.domain.qna.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * QuestionRepository.
 */

public interface QuestionRepository extends JpaRepository<Question, Long> {

  List<Question> findByBookId(Long bookId);
}
