package com.elice.bookstore.cart;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.elice.bookstore.cartbook.Repository.CartBookRepository;
import com.elice.bookstore.cartbook.dto.RequestCartBook;
import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CartTest {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private CartBookRepository cartBookRepository;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private JwtUtil jwtUtil;

 /* 임의 데이터(책, 유저 : 유저 이메일로 바로 조회)는 테스트 완료 .. */
  /*
  String secret = "BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234";

  @BeforeEach
  public void setup(){
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .build();

    jwtUtil = new JwtUtil(secret);
  }

  @DisplayName("유저 정보 가져오기 테스트")
  @Test

  public void User() throws Exception{
    String jwt = jwtUtil.createJwt("access", "user1@gmail.com", "USER", 60 * 10 * 1000L);

    String url = "/api/v1/cart";
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", jwt);

    ResultActions resultActions = mockMvc.perform(get(url)
        .headers(headers)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content((byte[]) null));

    MvcResult resultActions1 = resultActions.andReturn();

    String Jwt1 = resultActions1.getRequest().getHeader("Authorization");
    System.out.println("토큰은 " + Jwt1);

    String email = jwtUtil.getEmail(Jwt1);
    Boolean isValid = jwtUtil.isValid(Jwt1);
  }

    @DisplayName("장바구니 내역 조회 테스트") //장바구니 가지고 있던 회원
    @Test
    public void findCart() throws Exception {
      //given
      String url = "/api/v1/cart";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "1"); //user 가져오기 위함..

      //when
      ResultActions resultActions = mockMvc.perform(get(url)
              .headers(headers)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .content((byte[]) null)); //조회시에는 유저 정보(토큰)만 있으면 됨

      //then
      resultActions
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].itemName").value("책1"));
    }


    @DisplayName("장바구니 담기 테스트")
    @Test
    public void addCartBook() throws Exception {
      //given
      String url = "/api/v1/cart";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "1");

      long id = 29; //필요 없음
      long bookId = 3;
      int count = 2;

      RequestCartBook requestCartBook = new RequestCartBook(id, bookId, count);

      //when
      ResultActions resultActions = mockMvc.perform(post(url)
          .headers(headers)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(objectMapper.writeValueAsString(requestCartBook)));

      //then
      resultActions
          .andExpect(status().isOk())
          .andDo(print())
          .andExpect(jsonPath("$.itemName").value("책3"));
    }

    @DisplayName("개수 수정 테스트")
    @Test
    public void patchCartBook() throws Exception {
      //given
      String url = "/api/v1/cart";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "1");

      long id = 30;
      long bookId = 3;
      int count = 2;

      RequestCartBook requestCartBook = new RequestCartBook(id, bookId, count);

      //when
      ResultActions resultActions = mockMvc.perform(put(url)
          .headers(headers)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(objectMapper.writeValueAsString(requestCartBook)));

      //then
      resultActions
          .andExpect(status().isOk())
          .andDo(print())
          .andExpect(jsonPath("$.count").value(Integer.toString(count)));
    }


  @DisplayName("상품 삭제 테스트")
  @Test
  public void deleteCartBook() throws Exception {
    //given
    String url = "/api/v1/cart";
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "1");

    long id = 24;
    long bookId = 3;
    int count = 2;

    RequestCartBook requestCartBook = new RequestCartBook(id, bookId, count);

    //when
    ResultActions resultActions = mockMvc.perform(delete(url)
        .headers(headers)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(requestCartBook)));

    //then
    resultActions
        .andExpect(status().isOk());

    Assertions.assertThat( cartBookRepository.findById(id) == null);
  }
  */


}
