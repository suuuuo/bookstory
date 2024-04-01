package com.elice.bookstore.cart.domain;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.domain.CartBookDto;
import com.elice.bookstore.cartbook.domain.CartBookRepository;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.domain.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
