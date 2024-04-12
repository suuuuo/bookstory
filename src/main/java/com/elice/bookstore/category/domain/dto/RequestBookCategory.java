package com.elice.bookstore.category.domain.dto;

import com.elice.bookstore.book.domain.dto.RequestBook;
import java.util.List;
import lombok.Data;

@Data
public class RequestBookCategory {
   private String itemName;
   private Integer price;
   private String author;
   private String description;
   private String publisher;
   private final List<RequestBook> books;

}
