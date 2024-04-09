package com.elice.bookstore.category.service;


import com.elice.bookstore.category.domain.BookCategory;
import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

  @Autowired
  private final CategoryRepository categoryRepository;


  public Category create(Category category){
    categoryRepository.save(category);
    return category;
  }

  public Category updating(Category category){

    categoryRepository.save(category);
  return category;
  }

  public Optional<Category> read(Long categoryId){
    Optional<Category> category = categoryRepository.findById(categoryId);

    return category;
  }


  public void delete(Category category){
    categoryRepository.delete(category);
  }


  public String bringHighRankCategory(Category category){
    try {
      Category parent = category.getParent();
      return categoryRepository.findById(parent.getId()).get().getName();
    }catch (NullPointerException e){
      return null;
    }
  }


  public List<String> bringHighRankCategoryAll(Category category){
    List<String> categoryAll = new ArrayList<>();
      categoryAll.add(category.getName());

      while(true) {
      try {
        System.out.println("category = " + category);
        categoryAll.add(0, category.getParent().getName());
        System.out.println("category = " + category);
        category = categoryRepository.findById(category.getParent().getId()).get();
        System.out.println("category = " + category);
      } catch (NullPointerException e) {
        break;
      }
    }
    return categoryAll;
  }

  public List<Category> findByLevelAll(Integer level){
    return categoryRepository.findAllByLevel(level);
  }



}
