package com.elice.bookstore.cart.domain;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.domain.CartBookRepository;
import com.elice.bookstore.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

  private final CartRepository cartRepository;
  private final CartBookRepository cartBookRepository;

  public CartService(CartRepository cartRepository, CartBookRepository cartBookRepository) {
    this.cartRepository = cartRepository;
    this.cartBookRepository = cartBookRepository;
  }

  public Page<CartBook> findCarBooksByCart(Cart cart, PageRequest pageRequest){
    return cartBookRepository.findAllByCartOrderByCreatedAtDesc(cart, pageRequest);
  }

  public CartBook findCartBookByCartIdAndBookId(Cart cart, Book book){
    return cartBookRepository.findByCartIdAndBookId(cart.getId(), book.getId());
  }

  public Cart findCart(User user){ //유저 정보로 장바구니 가져오기

    Cart cart = cartRepository.findByUserId(user.getId());

    if(cart == null){ // 유저에게 장바구니가 없으면
      cart = new Cart(user); // 장바구니 생성
      cartRepository.save(cart);
    }

    return cart;
  }


  public List<CartBook> findAllCartBook(Cart cart){ //장바구니에 담긴 상품 리스트 조회
    List<CartBook> allcartBooks = cartBookRepository.findAll(); //모든 상품 리스트
    List<CartBook> cartBooks = new ArrayList<>(); //해당 장바구니의 상품을 담을 리스트

    for(CartBook cartBook : allcartBooks){
      if(cartBook.getCart().getId() == cart.getId()){ //해당 장바구니에 담겨있는 상품들만 담는다
        cartBooks.add(cartBook);
      }
    }

    if(cartBooks.size()==0){
    }
    return  cartBooks;
  }

  public Cart addCartBook(User user, Book book, int count ){ //장바구니에 상품 추가
    Cart cart = findCart(user);
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

  public void deleteCartBook(CartBook cartBook){
    long bookId = cartBook.getBook().getId();
    cartBookRepository.deleteByBookId(bookId);
  }

}
