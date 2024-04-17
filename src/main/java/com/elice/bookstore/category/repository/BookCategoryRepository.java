package com.elice.bookstore.category.repository;

import com.elice.bookstore.category.domain.BookCategory;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory,Long> {

  Optional<BookCategory> findById(Long id);
  Optional<BookCategory> findByBookIdAndCategoryId(Long bookId,Long CategoryId);
}
