package com.elice.bookstore.cart.repository;

import com.elice.bookstore.cart.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
  Cart findById(Long userId);

  List<Cart> findAll();

}
