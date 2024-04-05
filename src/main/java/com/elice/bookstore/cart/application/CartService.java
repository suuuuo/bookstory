package com.elice.bookstore.cart.application;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cart.repository.CartRepository;
import com.elice.bookstore.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  @Autowired
  private CartRepository cartRepository;

  // 토큰으로 유저 정보 가져오는 걸로 수정 해야
  // @Autowired
  // private UserRepository userRepository;

  /** 장바구니 조회 */
  @Transactional
  public Cart findCart(long userId) { // 유저 정보로 장바구니 가져오기
    Cart cart = cartRepository.findByUserId(userId);

    if(cart == null){ // 유저에게 장바구니가 없으면 유저 정보 가져와서 장바구니 생성
      //   User user = userRepository.findById(userId); // user는 cart정보 가지고 있지 않으므로 조회
      //   cart = new Cart(user);
      cartRepository.save(cart);
    }
    return cart;
  }

}