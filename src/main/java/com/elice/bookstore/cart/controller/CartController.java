package com.elice.bookstore.cart.controller;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.service.BookService;
import com.elice.bookstore.cartbook.application.CartBookService;
import com.elice.bookstore.cartbook.dto.RequestCartBook;
import com.elice.bookstore.cartbook.dto.ResponseCartBook;
import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** cart controller */
@RestController
@RequestMapping("/api")
public class CartController {

  @Autowired private CartBookService cartBookService;
  @Autowired private BookService bookService;
  @Autowired private JwtUtil jwtUtil;

  /** 장바구니 화면 : 담긴 상품들 조회 -> user id? loadUserByUsername */
  @GetMapping("/v1/cart")
  public ResponseEntity<List<ResponseCartBook>> CartPageTest(
      @RequestHeader("access") String authorizationHeader /*토큰으로 조회.. 추후 수정*/) {

    Long id = Long.parseLong(jwtUtil.getId(authorizationHeader));
    Boolean isValid = jwtUtil.isValid(authorizationHeader);

    if (isValid && id != null) {
      List<ResponseCartBook> cartBooks = cartBookService.findAllCartBook(id);
      return ResponseEntity.ok(cartBooks);
    }
    return null; // 유효하지 않으면? -> 로그인 창으로?
  }

  /** 장바구니에 책 담기 ; 장바구니 담기 버튼;처음 담는 상품 추가, 이미 있는 상품은 count만 증가 */
  @PostMapping("/v1/cart")
  public ResponseEntity<ResponseCartBook> CartAddTest(
      @RequestHeader("access") String authorizationHeader,
      @RequestBody RequestCartBook requestCartBook) {

    Long id = Long.parseLong(jwtUtil.getId(authorizationHeader));
    Boolean isValid = jwtUtil.isValid(authorizationHeader);

    if (isValid && id != null) {
    Book book = bookService.findById(requestCartBook.bookId()); // 책 아이디로 책 받아옴
      ResponseCartBook c = cartBookService.AddCartBook(id, book, requestCartBook.count());
    return ResponseEntity.ok(c);
    }
    return null;
  }

  /** 장바구니에 책 담기 ; 웹에서 조작한 상품 개수 반영 */
  @PutMapping("/v1/cart")
  public ResponseEntity<ResponseCartBook> CartPatch(
      @RequestHeader("access") String authorizationHeader,
      @RequestBody RequestCartBook requestCartBook) {

    Long id = Long.parseLong(jwtUtil.getId(authorizationHeader));
    Boolean isValid = jwtUtil.isValid(authorizationHeader);

    if (isValid && id != null) {
    ResponseCartBook c =
        cartBookService.PatchCartBook(requestCartBook.id(), requestCartBook.count());
      return ResponseEntity.ok(c);
    }
    return null;
  }

  /*
   *장바구니에서 상품 삭제
   */
  @DeleteMapping("/v1/cart/{cartBookId}")
  public void DeleteCartBook(
      @RequestHeader("access") String authorizationHeader, @PathVariable long cartBookId) {
    // 프론트엔드에서 체크박스 상태 확인해서 체크된 것들만 요청
    Long id = Long.parseLong(jwtUtil.getId(authorizationHeader));
    Boolean isValid = jwtUtil.isValid(authorizationHeader);
    if (isValid && id != null) {
      cartBookService.DeleteCartBook(cartBookId);
    }
  }
}
