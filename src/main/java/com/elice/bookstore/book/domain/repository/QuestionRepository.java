package com.elice.bookstore.book.domain.repository;

import com.elice.bookstore.book.domain.qna.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {
}
