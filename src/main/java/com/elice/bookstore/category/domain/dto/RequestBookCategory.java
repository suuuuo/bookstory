package com.elice.bookstore.category.domain.dto;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.RequestBook;
import com.elice.bookstore.category.domain.Category;
import java.util.List;
import lombok.Data;

@Data
public class RequestBookCategory {
   private Book book;
   private com.elice.bookstore.category.domain.Category Category;
}
