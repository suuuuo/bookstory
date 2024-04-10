package com.elice.bookstore.cartbook.application;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cart.application.CartService;
import com.elice.bookstore.cartbook.Mapper.CartBookMapper;
import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.Repository.CartBookRepository;
import com.elice.bookstore.cartbook.dto.ResponseCartBook;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartBookService {

  @Autowired
  private CartBookRepository cartBookRepository;

  @Autowired
  private CartService cartService;

  @Autowired private CartBookMapper cartBookMapper;

  public List<ResponseCartBook> findAllCartBook(
      Long id) { // 장바구니에 담긴 상품 리스트 조회 controller - get
    Cart cart = cartService.findCart(id);

    List<CartBook> allCartBooks = cartBookRepository.findCartBookByCartId(cart.getId());

    List<ResponseCartBook> responseCartbooks = new ArrayList<>();
    for (CartBook cartBook : allCartBooks) {
      responseCartbooks.add(cartBookMapper.CartBookToResponseCartBook(cartBook));
    }
    return responseCartbooks;
  }

  /** 장바구니 상품 담기/개수변경 */
  @Transactional
  public ResponseCartBook AddCartBook(Long id, Book book, int count) { // 장바구니에 상품 추가
    Cart cart = cartService.findCart(id);
    CartBook cartBook = cartBookRepository.findByCartIdAndBookId(cart.getId(), book.getId());

    if(cartBook == null){ //장바구니에 담긴 적 없는 상품이면 생성
      cartBook = new CartBook(book, cart, count);
      cartBookRepository.save(cartBook);
    }
    else{
      cartBook.setCount(cartBook.getCount() + count);
    }
    return cartBookMapper.CartBookToResponseCartBook(cartBook);
  }

  @Transactional
  public ResponseCartBook PatchCartBook(long cartBookId, int count) {
    CartBook cartBook = cartBookRepository.findById(cartBookId);
    cartBook.setCount(count);

    return cartBookMapper.CartBookToResponseCartBook(cartBook);
  }

  /** 삭제 */
  @Transactional
  public void DeleteCartBook(long cartBookId) {
    CartBook cartBook = cartBookRepository.findById(cartBookId);
    cartBookRepository.deleteById(cartBook.getId());
  }

}
