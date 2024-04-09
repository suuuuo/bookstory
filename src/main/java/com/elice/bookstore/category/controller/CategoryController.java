package com.elice.bookstore.category.controller;

import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.domain.dto.RequestCategory;
import com.elice.bookstore.category.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CategoryController {

  @Autowired
  private final CategoryService categoryService;

  @GetMapping("/v1/bringCategory")
  public List<RequestCategory> categories(){
    List<Category> findCategoriesByLevel = categoryService.findByLevelAll(1);

    List<RequestCategory> findCategoryDtoByLevel = findCategoriesByLevel.stream()
        .map(m -> new RequestCategory(m.getName(),1))
        .toList();
    return findCategoryDtoByLevel;
  }

}
