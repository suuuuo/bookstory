package com.elice.bookstore.cart;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.cart.application.CartService;
import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.cartbook.application.CartBookService;
import com.elice.bookstore.cartbook.domain.CartBook;
import com.elice.bookstore.cartbook.dto.CartBookDto;
import com.elice.bookstore.cartbook.mapper.CartBookMapper;
import com.elice.bookstore.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CartTest {
  @Autowired
  private CartService cartService;
  @Autowired
  private CartBookService cartBookService;
  @Autowired
  private CartBookMapper cartBookMapper;
  @Autowired
  private UserService userService;
  @Autowired
  private BookService bookService;


  @DisplayName("장바구니 내역 조회 테스트 1") //장바구니 가지고 있던 회원
  @Test
  public void findCart(){
    //given
    User user = userService.findUser(1);
    //when
    List<CartBook> cartBooks= cartBookService.findAllCartBook(user.getId());
    List<CartBookDto> cartBookDtos = cartBooks.stream()
        .map(CartBookMapper::ToDto)
        .collect(Collectors.toList());
    //then
    Assertions.assertThat(cartBooks.get(0).getId().equals(20));
    Assertions.assertThat(cartBooks.get(0).getBook().getItemName().equals("책2"));
    Assertions.assertThat(cartBookDtos.get(0).getItemName().equals("책2"));
    Assertions.assertThat(cartBookDtos.get(0).getPrice()==20000);
    Assertions.assertThat(cartBookDtos.get(0).getBookId()==2);
    Assertions.assertThat(cartBookDtos.get(0).getCartId()==1);
  }


  @DisplayName("장바구니 내역 조회 테스트 2") //장바구니 가지고 있지 않던 회원
  @Test
  public void findNewCart(){
    //given
    User user = userService.findUser(8);
    //when
    List<CartBook> cartBooks= cartBookService.findAllCartBook(user.getId());
    List<CartBookDto> cartBookDtos = cartBooks.stream()
        .map(CartBookMapper::ToDto)
        .collect(Collectors.toList());
    //then
    Assertions.assertThat(cartBooks.equals(null));
    Assertions.assertThat(cartBookDtos.equals(null));
    Cart cart = cartService.findCart(user.getId());
    Assertions.assertThat(cart.getUser().getId()==8l);
  }


  @DisplayName("장바구니 담기 테스트 1") //없는 상품 담았을 때
  @Test
  public void addCartBook(){
    //given
    User user = userService.findUser(1);
    Book book = bookService.findBook(1);
    int count = 2;
    //when
    CartBook cartBook = cartBookService.addCartBook(user.getId(),book,count);
    CartBookDto cartBookDto = cartBookMapper.ToDto(cartBook);
    //then
    Assertions.assertThat(cartBook.getBook().getId().equals(1));
    Assertions.assertThat(cartBook.getCount().equals(5));
    Assertions.assertThat(cartBook.getId().equals(21));
    Assertions.assertThat(cartBookDto.getBookId()==1);
    Assertions.assertThat(cartBookDto.getPrice()==10000);
    Assertions.assertThat(cartBookDto.getItemName().equals("책1"));
  }

  @DisplayName("장바구니 담기 테스트 2") //있는 상품 담았을 때
  @Test
  public void addCartBookExist(){
    //given
    User user = userService.findUser(1);
    Book book = bookService.findBook(2);
    int count = 2;
    //when
    CartBook cartBook = cartBookService.addCartBook(user.getId(),book,count);
    CartBookDto cartBookDto = cartBookMapper.ToDto(cartBook);
    //then
    Assertions.assertThat(cartBook.getBook().getId().equals(2));
    Assertions.assertThat(cartBook.getCount().equals(2));
    Assertions.assertThat(cartBook.getId().equals(22));
    Assertions.assertThat(cartBookDto.getCount()==2);
    Assertions.assertThat(cartBookDto.getItemName().equals("책2"));
  }

  @DisplayName("개수 수정 테스트")
  @Test
  public void patchCartBook(){
    //given
    User user = userService.findUser(1);
    List<CartBook> cartBooks= cartBookService.findAllCartBook(user.getId());
    //when
    cartBookService.patchCartBook(cartBooks.get(0).getId(), 5);
    cartBookService.patchCartBook(cartBooks.get(1).getId(), 3);
    List<CartBookDto> cartBookDtos = cartBooks.stream()
        .map(CartBookMapper::ToDto)
        .collect(Collectors.toList());
    //then
    Assertions.assertThat(cartBooks.get(0).getCount().equals(5));
    Assertions.assertThat(cartBooks.get(1).getCount().equals(3));
    Assertions.assertThat(cartBookDtos.get(0).getItemName().equals("책1"));
    Assertions.assertThat(cartBookDtos.get(1).getItemName().equals("책2"));
  }

  @DisplayName("상품 삭제 테스트")
  @Test
  public void deleteCartBook(){
    //given
    User user = userService.findUser(1);
    List<CartBook> cartBooks= cartBookService.findAllCartBook(user.getId());
    //when
    cartBookService.deleteCartBook(cartBooks.get(0).getId());
    List<CartBookDto> cartBookDtos = cartBooks.stream()
        .map(CartBookMapper::ToDto)
        .collect(Collectors.toList());
    //then
    Assertions.assertThat(cartBooks.size() == 1);
    Assertions.assertThat(cartBookDtos.get(0).getBookId()==2);
    Assertions.assertThat(cartBookDtos.size()==1);
  }

}

