package com.elice.bookstore.book.domain;

import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.config.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * book domain.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Column
  private String itemName;

  @Column
  private Integer price;

  @Column
  private Integer stock;

  @Column
  private String imgPath;

  @Column
  private String author;

  @Column
  private String isbn;

  @Enumerated(EnumType.STRING)
  private BookSellStatus sellStatus;

  @Column
  private Integer sellCount;

  @Column
  private String publisher;

  @Column
  private String description;


  public void update(String itemName, Integer price, String author, String description, String publisher) {
    this.itemName = itemName;
    this.price = price;
    this.author = author;
    this.description = description;
    this.publisher = publisher;
  }
}
