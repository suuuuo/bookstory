package com.elice.bookstore.cart.domain;

import com.elice.bookstore.cart.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
  Cart findByUserId(long userId);

  //Cart findByCartId(long cartId);

}