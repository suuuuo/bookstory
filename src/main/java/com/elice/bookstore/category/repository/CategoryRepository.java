package com.elice.bookstore.category.repository;

import com.elice.bookstore.category.domain.BookCategory;
import com.elice.bookstore.category.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

  Optional<Category> findById(Long id);
  List<BookCategory> findByNameLike(String name);
  List<Category> findAllByLevel(Integer level);

}
