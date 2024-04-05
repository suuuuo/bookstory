package com.elice.bookstore.cartbook.Repository;

import com.elice.bookstore.cartbook.domain.CartBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartBookRepository extends JpaRepository<CartBook, Integer> {

  CartBook findByCartIdAndBookId(long cartId, long bookId);

  List<CartBook> findCartBookByCartId(long cartId);

  void deleteById(long cartBookId);

  CartBook findById(long cartBookId);
}
