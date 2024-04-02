package com.elice.bookstore.cart.domain;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.BookService;
import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.domain.CartBookDto;
import com.elice.bookstore.cartbook.domain.CartBookMapper;
import com.elice.bookstore.cartbook.domain.CartBookService;
import com.elice.bookstore.user.domain.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * cart controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;
  private final CartBookService cartBookService;
  private final BookService bookService;
  private final CartBookMapper cartBookMapper;

  public CartController(CartService cartService, CartBookService cartBookService,
      BookService bookService, CartBookMapper cartBookMapper) {
    this.cartService = cartService;
    this.cartBookService = cartBookService;
    this.bookService = bookService;
    this.cartBookMapper = cartBookMapper;
  }

  /**
   * 장바구니 화면 : 담긴 상품들 조회
   */
  @GetMapping("")
  public List<CartBookDto> CartPageTest(@RequestParam long userId) {
    List<CartBook> cartBooks = cartBookService.findAllCartBook(userId);
    List<CartBookDto> cartBookDtos = cartBooks.stream()
        .map(CartBookMapper::ToDto)
        .collect(Collectors.toList());

    int totalPrice = 0;
    for (CartBook cartBook : cartBooks) {
      totalPrice += cartBook.getCount() * cartBook.getBook().getPrice(); // 총 가격
    }
    int cartCount = cartBooks.size(); // 장바구니 담겨있는 상품 종류 수
    return cartBookDtos;
  }


  /**
   * 장바구니에 책 담기 ; 이미 있는 상품 담으면 count만 증가
   */
  @PostMapping("/{book_id}")
  public CartBookDto CartAddTest(
      @RequestParam long userId, @PathVariable(value = "book_id") long bookId) {
    int count = 1; // 웹에서 가져와야?
    Book book = bookService.findBook(bookId); // 책 아이디로 책 받아옴
    return CartBookMapper.ToDto(cartBookService.addCartBook(userId, book, count));
  }

  /**
   * 웹에서 조작한 상품 개수 반영
   */
  @PutMapping("/{cartBook_id}")
  public CartBookDto CartPatch(
      @PathVariable(value = "cartBook_id") long cartBookId, @RequestParam int count){
    CartBook cartBook = cartBookService.patchCartBook(cartBookId, count);
    return cartBookMapper.ToDto(cartBook);
  }


  /**
   * 장바구니에서 상품 삭제
   */
  @DeleteMapping("{cartbook_id}")
  public void DeleteCartBook(
      @PathVariable(value = "cartBook_id") long cartBookId) {

    // 실제 동작시에는 웹에서 선택된 것들만 리스트로 받는 : 책 id로 받?
    cartBookService.deleteCartBook(cartBookId);
  }
}
