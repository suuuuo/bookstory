package com.elice.bookstore.cartbook.domain;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cartbook.domain.CartBook;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartBookRepository extends JpaRepository<CartBook,Integer> {

  Page<CartBook> findAllByCartOrderByCreatedAtDesc(Cart cart, Pageable pageable);
  CartBook findByCartIdAndBookId(long cartId,long bookId);

  List<CartBook> findCartBookByCartId(long cartId);

  void deleteByBookId(long bookId);
}
