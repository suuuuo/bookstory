package com.elice.bookstore.cart.domain;

import com.elice.bookstore.book.domain.Book;
//import com.elice.bookstore.book.domain.BookService;
import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
//import com.elice.bookstore.user.domain.UserService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
  private final CartService cartService;
  //private final UserService userService;
  //private final BookService bookService;

  //user, book 관련 코드들 작성되면 수정해야

  public CartController(CartService cartService){  //, UserService userService, BookService bookService) {
    this.cartService = cartService;
    //this.userService = userService;
    //this.bookService = bookService;
  }


  @GetMapping("/{user_id}") // postman 테스트 완료(User, Book 임의 데이터, repository, service)
  public List<CartBook> CartPageTest(@PathVariable(value="user_id") long userId){
    User user = userService.findUser(userId); //유저 아이디로 유저 받아옴
    Cart cart = cartService.findCart(user);
    List<CartBook> cartBooks = cartService.findAllCartBook(cart);

    int totalPrice = 0;

    for(CartBook cartBook :cartBooks){
      totalPrice += cartBook.getCount() * cartBook.getBook().getPrice(); //총 가격
    }

    int cartCount = cartBooks.size();
    return cartBooks;
  }


  @PostMapping("/{user_id}/{book_id}") // postman 테스트 완료
  public List<CartBook> CartAddTest(@PathVariable(value="user_id") long userId,
      @PathVariable(value="book_id") long bookId){
    User user = userService.findUser(userId); //유저 아이디로 유저 받아옴
    Cart cart = cartService.findCart(user);

    int count = 1; //웹에서 가져와야?

    Book book = bookService.findBook(bookId); // 책 아이디로 책 받아옴

    cartService.addCartBook(user, book, count);
    List<CartBook> cartBooks = cartService.findAllCartBook(cart);

    System.out.println(cartBooks.size());

    return cartBooks;

  }

  @DeleteMapping("/{user_id}/{book_id}") //장바구니 물품 삭제 .. postman 테스트 완료( cartbook_id로 삭제되는 기능만 일단 확인)
  public List<CartBook> DeleteCartBook(@PathVariable(value="user_id") long userId,
      @PathVariable(value="book_id") long bookId){
    User user = userService.findUser(userId); //유저 아이디로 유저 받아옴
    Cart cart = cartService.findCart(user);
    Book book = bookService.findBook(bookId); //책 아이디로 책 받아옴

    //실제 동작시에는 웹에서 선택된 것들만 리스트로 받는 : 책 id로 받?
    CartBook cartBook = cartService.findCartBookByCartIdAndBookId(cart, book);
    cartService.deleteCartBook(cartBook);

    List<CartBook> cartBooks = cartService.findAllCartBook(cart);

    return cartBooks;
  }

}
