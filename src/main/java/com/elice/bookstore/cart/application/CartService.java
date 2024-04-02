package com.elice.bookstore.cart.application;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cart.repository.CartRepository;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.domain.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

  private final CartRepository cartRepository;
  private final UserService userService;

  public CartService(CartRepository cartRepository, UserService userService) {
    this.cartRepository = cartRepository;
    this.userService = userService;
  }

  /*
  public Page<CartBook> findCarBooksByCart(Cart cart, PageRequest pageRequest){
    return cartBookRepository.findAllByCartOrderByCreatedAtDesc(cart, pageRequest);
  }
   */


  /**
   * 장바구니 조회
   */

  public Cart findCart(long userId){ //유저 정보로 장바구니 가져오기
    Cart cart = cartRepository.findByUserId(userId);

    if(cart == null){ // 유저에게 장바구니가 없으면
      User user = userService.findUser(userId); //유저 정보 가져와서
      cart = new Cart(user); // 장바구니 생성
      cartRepository.save(cart);
    }
    return cart;
  }

}
