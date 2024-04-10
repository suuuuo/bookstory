package com.elice.bookstore.book.domain.repository;

import com.elice.bookstore.book.domain.qna.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByBookId(Long bookId);
}
