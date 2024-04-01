package com.elice.bookstore.cartbook.domain;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cart.domain.CartService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartBookService {

  private final CartBookRepository cartBookRepository;

  private final CartService cartService;

  public CartBookService(CartBookRepository cartBookRepository, CartService cartService) {
    this.cartBookRepository = cartBookRepository;
    this.cartService = cartService;
  }


  /**
   * 조회
   */
  public List<CartBook> findAllCartBook(long userId){ //장바구니에 담긴 상품 리스트 조회 controller - get
    Cart cart = cartService.findCart(userId);

    List<CartBook> allCartBooks = cartBookRepository.findCartBookByCartId(cart.getId());
    return allCartBooks;
  }

  /**
   * 장바구니 상품 담기/개수변경
   */

  public Cart addCartBook(long userId, Book book, int count){ //장바구니에 상품 추가
    Cart cart = cartService.findCart(userId);
    CartBook cartBook = cartBookRepository.findByCartIdAndBookId(cart.getId(), book.getId());

    if(cartBook == null){ //장바구니에 담긴 적 없는 상품이면 생성
      cartBook = new CartBook(book, cart, count);
      cartBookRepository.save(cartBook);
    }
    else{ //fetch
      cartBook.setCount(cartBook.getCount() + count);
    }

    return cart;
  }

  public CartBook patchCartBook(long userId, long bookId,int count){
    CartBook fetchCartBook = cartBookRepository.findByCartIdAndBookId(cartService.findCart(userId).getId(), bookId);
    fetchCartBook.setCount(count);

    return cartBookRepository.save(fetchCartBook);
  }


  /**
   * 삭제
   */
  public void deleteCartBook(long cartId, long bookId){
    CartBook cartBook = cartBookRepository.findByCartIdAndBookId(cartId, bookId);
    cartBookRepository.deleteById(cartBook.getId());
  }
}
