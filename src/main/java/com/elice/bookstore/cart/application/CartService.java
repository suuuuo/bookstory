package com.elice.bookstore.cart.application;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cart.repository.CartRepository;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  @Autowired private CartRepository cartRepository;
  @Autowired private UserRepository userRepository;

  /** 장바구니 조회 */
  @Transactional
  public Cart findCart(Long id) { // 유저 정보로 장바구니 가져오기
    Cart cart = cartRepository.findById(id);

    if (cart == null) { // 유저에게 장바구니가 없으면 유저 정보 가져와서 장바구니 생성
      Optional<User> user =
          userRepository.findByIdAndIsExist(id, true); // user는 cart정보 가지고 있지 않으므로 조회
      cart = new Cart(user.get());
      cartRepository.save(cart);
      return cart;
    }
    return cart;
  }
}
