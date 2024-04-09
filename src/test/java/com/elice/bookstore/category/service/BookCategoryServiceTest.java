package com.elice.bookstore.category.service;

import static org.junit.jupiter.api.Assertions.*;

import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.repository.BookCategoryRepository;
import com.elice.bookstore.category.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback
@Transactional
class BookCategoryServiceTest {

  @Autowired
  BookCategoryRepository bookCategoryRepository;
  @Autowired
  BookCategoryService bookCategoryService;

  @Test
  public void 생성() {
    //given
    Category category = new Category();
    category.setName("소설");
    category.setLevel(3);
  }

  @Test
  public void 삭제() {
    //given
    //when
    //then

  }


}