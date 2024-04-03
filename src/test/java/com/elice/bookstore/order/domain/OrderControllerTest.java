package com.elice.bookstore.order.domain;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.delivery.domain.Delivery;
import com.elice.bookstore.delivery.domain.DeliveryService;
import com.elice.bookstore.orderbook.domain.OrderBook;
import com.elice.bookstore.orderbook.domain.OrderBookService;
import com.elice.bookstore.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@DisplayName("주문 관련 기능 테스트")
public class OrderControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private OrderService orderService;
  @MockBean
  private OrderBookService orderBookService;
  @MockBean
  private DeliveryService deliveryService;

  private Order order;
  private User user;
  private Cart cart;
  private Book book;
  private OrderBook orderBook;
  private Delivery delivery;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(222L);

    cart = new Cart();
    cart.setId(777L);

    book = new Book();
    book.setId(234732L);

    order = new Order();
    order.setId(111L);

    orderBook = new OrderBook();
    orderBook.setId(333276236L);
  }

  @Test
  @DisplayName("회원 : 나의 주문 내역 조회")
  void getAllMyOrdersTest() throws Exception {
    // given
    OrderBook orderBook = new OrderBook();
    orderBook.setId(333276236L);
    orderBook.setUser(user);
    orderBook.setOrder(order);
    orderBook.setBook(book);
    orderBook.setCount(2);


    Pageable pageable = PageRequest.of(0, 15);
    PageImpl<OrderBook> orderBookPage = new PageImpl<>(Collections.singletonList(orderBook), pageable, 1);
    when(orderBookService.findAllByUserIdOrderByCreatedAtDesc(anyLong(), any(Pageable.class)))
                          .thenReturn(orderBookPage);

    // when
    ResultActions result = mockMvc.perform(get("/orders/{id}", user.getId())
                                  .param("page","0")
                                  .param("size", "15")
                                  .contentType(MediaType.APPLICATION_JSON));
    // then
    result.andExpect(status().isOk())
          .andExpect(jsonPath("$.content[0].id")
          .value(orderBook.getId().intValue()));
  }

  @Test
  @DisplayName("회원 : 주문하기")
  void saveOrderTest() throws Exception {
    // given
    Order order = new Order();
    order.setId(111L);
    order.setUser(user);
    order.setCart(cart);
    order.setOrderDate(LocalDate.now());
    order.setOrderStatus(OrderStatus.NEW);
    order.setPaymentDate(LocalDate.now());
    order.setTotalPrice(20000);
    order.setIsExist(false);

    // when
    ResultActions result = mockMvc.perform(put("/orders/order"));
    // then
    result.andExpect(status().isCreated());
  }

  @Test
  @DisplayName("회원 : 배송 전, 주문 정보 수정")
  void updateOrderTest() throws Exception {
    // given
    Delivery delivery = new Delivery();
    delivery.setOrder(order);
    Map<String, String> params = new HashMap<>();
    params.put("address", "한국 어딘가");
    params.put("phoneNumber", "010-7777-8888");

    // when
    ResultActions result = mockMvc.perform(put("/orders/{id}/update", order.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(params)));
    // then
    result.andExpect(status().isOk())
        .andExpect(content().string("하나둘셋 성공이닷!"));
  }

  @Test
  @DisplayName("회원: 배송 전 주문취소")
  void cancelOrderTest() throws Exception {
    // given
    Long id = 23414352624L;

    // when
    ResultActions result = mockMvc.perform(put("/orders/{id}/cancel", id));
    // then
    result.andExpect(status().isOk());
  }

  @Test
  @DisplayName("관리자: 전체 주문 내역 조회")
  void adminGetAllOrdersTest() throws Exception {
    // given
    OrderBook orderBook = new OrderBook();
    orderBook.setId(333276236L);
    orderBook.setUser(user);
    orderBook.setOrder(order);
    orderBook.setBook(book);
    orderBook.setCount(2);

    Pageable pageable = PageRequest.of(0, 15);
    PageImpl<OrderBook> orderBookPage = new PageImpl<>(Collections.singletonList(orderBook), pageable, 1);
    when(orderBookService.findAllByOrderByCreatedAtDesc(any(Pageable.class)))
        .thenReturn(orderBookPage);

    // when
    ResultActions result = mockMvc.perform(get("/admin/orders")
        .param("page","0")
        .param("size", "15"));
    // then
    result.andExpect(status().isOk());
  }

  @Test
  @DisplayName("관리자: 주문 상태 변경")
  void adminUpdateOrderStatusTest() throws Exception {
    // given
    Order order = new Order();
    order.setOrderStatus(OrderStatus.PENDING);
    order.setId(12346127854L);

    // when
    ResultActions result = mockMvc.perform(put("/admin/orders/{id}/{orderStatus}", order.getId(), order.getOrderStatus()));
    // then
    result.andExpect(status().isOk());
  }

  @Test
  @DisplayName("관리자: 주문 내역 삭제")
  void adminDeleteOrderTest() throws Exception {
    // given
    Order order = new Order();
    order.setId(95678923L);

    doNothing().when(orderService).deleteById(order.getId());
    // when & then
    mockMvc.perform(delete("/admin/orders/{id}", order.getId()))
            .andExpect(status().isOk());
  }

//  @Test
//  @DisplayName("필드누락 - 회원:주문하기")
//  void saveOrderFailed() throws Exception {
//    // given: 필수 필드 누락한 주문 정보
//    Order order = new Order();
//    order.setId(111L);
//    // 필수 필드인 user, cart, orderDate, totalPrice 등이 누락되었다고 가정
//
//    // when: 주문 저장 엔드포인트 호출
//    ResultActions result = mockMvc.perform(put("/orders/order"));
//    // then: BadRequest(400) 예외가 발생해야 함
//    result.andExpect(status().isBadRequest());
//  }
}
