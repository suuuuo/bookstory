package com.elice.bookstore.category.service;

import com.elice.bookstore.category.domain.BookCategory;
import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.repository.BookCategoryRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookCategoryService {

  @Autowired
  private final BookCategoryRepository bookCategoryRepository;


  public BookCategory create(BookCategory bookCategory){
    bookCategoryRepository.save(bookCategory);
    return bookCategory;
  }

  public BookCategory updating(BookCategory bookcategory){

    bookCategoryRepository.save(bookcategory);
    return bookcategory;
  }

  public Optional<BookCategory> read(Long bookCategoryId){
    Optional<BookCategory> bookCategory = bookCategoryRepository.findById(bookCategoryId);

    return bookCategory;
  }


  public void delete(BookCategory bookCategory){
    bookCategoryRepository.delete(bookCategory);
  }


}
