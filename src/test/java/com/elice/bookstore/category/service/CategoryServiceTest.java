package com.elice.bookstore.category.service;

import static org.junit.jupiter.api.Assertions.*;

import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.repository.CategoryRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
class CategoryServiceTest {

  @Autowired
  CategoryService categoryService;
  @Autowired
  CategoryRepository categoryRepository;

  @Test
  public void 생성(){
    //given
    Category category = new Category();
    category.setName("생성");
    category.setLevel(2);

    //when
    Category category1 = categoryService.create(category);
    category1.setLevel(1);
    categoryService.updating(category1);
    Category category2 =categoryService.read(category.getId()).get();


    //then
    Assertions.assertThat(category1.getLevel()).isEqualTo(category2.getLevel());
  }

  @Test
  public void 삭제(){
    //given
    Category category = new Category();
    category.setName("제목");
    categoryService.create(category);
    Long id = category.getId();
    //when
    categoryService.delete(category);
    //then
    Assertions.assertThat(categoryService.read(id)).isEmpty();
  }

  @Test
  public void 상위카테고리불러오기(){
    //given
    Category category1 = new Category();
    category1.setName("국내도서");
    category1.setLevel(1);
    Category category2 = new Category();
    category2.setName("소설");
    category2.setLevel(2);
    category2.setParent(category1);
    //when
    categoryService.create(category1);
    categoryService.create(category2);

   //then
    String name = categoryService.bringHighRankCategory(category2);
    Assertions.assertThat(name).isEqualTo(category1.getName());

    String name1 = categoryService.bringHighRankCategory(category1);
    Assertions.assertThat(name1).isEqualTo(null);
  }

  @Test
  public void 상위카테고리모두불러오기(){
    //given
    Category category1 = new Category();
    category1.setName("국내도서");
    category1.setLevel(1);
    categoryService.create(category1);

    Category category2 = new Category();
    category2.setName("문학");
    category2.setLevel(2);
    categoryService.create(category2);
    category2.setParent(category1);

    Category category3 = new Category();
    category3.setName("소설");
    categoryService.create(category3);
    category3.setLevel(3);
    category3.setParent(category2);


    Category category5 = new Category();
    category5.setName("추리소설");
    categoryService.create(category5);
    category5.setLevel(4);
    category5.setParent(category3);



    Category category4 = new Category();
    category4.setName("비문학");
    categoryService.create(category4);
    category4.setLevel(2);
    category4.setParent(category1);

    //when
    List<String> categories = categoryService.bringHighRankCategoryAll(category3);

    //then

    Assertions.assertThat(categories).contains(category1.getName());
    Assertions.assertThat(categories).contains(category2.getName());
    Assertions.assertThat(categories).contains(category3.getName());
    Assertions.assertThat(categories).doesNotContain(category4.getName());

  }

  @Test
  public void 카테고리레벨리스트모두불러오기(){

    Category category1 = new Category();
    category1.setName("국내도서");
    category1.setLevel(1);
    categoryService.create(category1);

    Category category2 = new Category();
    category2.setName("해외도서");
    category2.setLevel(1);
    categoryService.create(category2);

    Category category3 = new Category();
    category3.setName("일본도서");
    categoryService.create(category3);
    category3.setLevel(1);

    Category category4 = new Category();
    category4.setName("중국도서");
    category4.setLevel(2);
    categoryService.create(category4);

    List<Category> level1Category = categoryService.findByLevelAll(1);
    Assertions.assertThat(level1Category).contains(category1);
    Assertions.assertThat(level1Category).contains(category2);
    Assertions.assertThat(level1Category).doesNotContain(category4);

  }
}