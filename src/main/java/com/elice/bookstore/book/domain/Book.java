package com.elice.bookstore.book.domain;

import com.elice.bookstore.book.domain.dto.BookDetailResponse;
import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.config.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * book domain.
 */
@Entity
@Getter
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
  private BigDecimal price;

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
  @Lob
  private String description;


}
