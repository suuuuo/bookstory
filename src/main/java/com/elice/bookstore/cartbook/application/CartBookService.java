package com.elice.bookstore.cartbook.application;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cart.application.CartService;
import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.Repository.CartBookRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartBookService {

  @Autowired
  private CartBookRepository cartBookRepository;

  @Autowired
  private CartService cartService;

  /** 조회 */
  public List<CartBook> findAllCartBook(long userId) { // 장바구니에 담긴 상품 리스트 조회 controller - get
    Cart cart = cartService.findCart(userId);

    List<CartBook> allCartBooks = cartBookRepository.findCartBookByCartId(cart.getId());
    return allCartBooks;
  }

  /** 장바구니 상품 담기/개수변경 */
  @Transactional
  public CartBook addCartBook(long userId, Book book, int count) { // 장바구니에 상품 추가
    Cart cart = cartService.findCart(userId);
    CartBook cartBook = cartBookRepository.findByCartIdAndBookId(cart.getId(), book.getId());

    if(cartBook == null){ //장바구니에 담긴 적 없는 상품이면 생성
      cartBook = new CartBook(book, cart, count);
      cartBookRepository.save(cartBook);
    }
    else{
      cartBook.setCount(cartBook.getCount() + count);
    }

    return cartBook;
  }

  @Transactional
  public CartBook patchCartBook(long cartBookId, int count) {
    CartBook patchCartBook = cartBookRepository.findById(cartBookId);
    patchCartBook.setCount(count);
    return cartBookRepository.save(patchCartBook);
  }

  /** 삭제 */
  @Transactional
  public void deleteCartBook(long cartBookId) {
    CartBook cartBook = cartBookRepository.findById(cartBookId);
    cartBookRepository.deleteById(cartBook.getId());
  }
}
