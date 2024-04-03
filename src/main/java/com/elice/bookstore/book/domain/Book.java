package com.elice.bookstore.book.domain;

import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.config.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
