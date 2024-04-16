package com.elice.bookstore.book.domain.repository;

import com.elice.bookstore.book.domain.qna.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AnswerRepository.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
