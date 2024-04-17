package com.elice.bookstore.book.domain.repository;

import com.elice.bookstore.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BookRepository.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
