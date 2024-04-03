package com.elice.bookstore.category.service;

import static org.junit.jupiter.api.Assertions.*;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.category.domain.BookCategory;
import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.repository.BookCategoryRepository;
import com.elice.bookstore.category.repository.CategoryRepository;
import java.util.Optional;
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

  @Autowired
  CategoryService categoryService;

  @Test
  public void 생성() {
    //given
    BookCategory bookCategory = new BookCategory();

    Category category = new Category();
    category.setName("소설");
    Category category2 = new Category();
    category.setBookCategory(bookCategory);
    category2.setBookCategory(bookCategory);
    category.setName("문학");
    //when
    Category categoryA = categoryService.create(category);
    Category categoryB = categoryService.create(category2);
    BookCategory bookCategory1 = bookCategoryService.create(bookCategory);
    //then
    Assertions.assertThat(categoryA.getBookCategory()).isEqualTo(bookCategory1);
    Assertions.assertThat(categoryB.getBookCategory()).isEqualTo(bookCategory1);
  }

  @Test
  public void 삭제() {
    //given
    BookCategory bookCategory = new BookCategory();
    BookCategory bookCategory1 = bookCategoryService.create(bookCategory);
    Long id = bookCategory1.getId();
    //when
    bookCategoryService.delete(bookCategory1);
    //then
    Assertions.assertThat(bookCategoryService.read(id)).isEmpty();
  }




}