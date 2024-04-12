package com.elice.bookstore.category.controller;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.category.domain.BookCategory;
import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.domain.dto.RequestBookCategory;
import com.elice.bookstore.category.domain.dto.RequestCategory;
import com.elice.bookstore.category.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Builder
public class CategoryController {

  @Autowired
  private final CategoryService categoryService;

  @ResponseBody
  @GetMapping("/v1/bringCategory")
  public List<RequestCategory> categories(){
    System.out.println("_____________");
    List<Category> findCategoriesByLevel = categoryService.findByLevelAll(1);
    System.out.println("_____________");
    System.out.println("findCategoriesByLevel = " + findCategoriesByLevel);
    return findCategoriesByLevel.stream()
        .map(m -> new RequestCategory(m.getName(),1))
        .toList();
  }

  public RequestBookCategory requestBookCategory(){

    return null;
  }

}
