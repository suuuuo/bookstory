package com.elice.bookstore.cartbook.domain;

import com.elice.bookstore.cart.domain.Cart;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartBookRepository extends JpaRepository<CartBook,Integer> {

  Page<CartBook> findAllByCartOrderByCreatedAtDesc(Cart cart, Pageable pageable);
  CartBook findByCartIdAndBookId(long cartId,long bookId);

  List<CartBook> findCartBookByCartId(long cartId);
  void deleteById(long cartBookId);

  CartBook findById(long cartBookId);
}
