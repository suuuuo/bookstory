package com.elice.bookstore.order.domain;

import com.elice.bookstore.cart.domain.Cart;
import com.elice.bookstore.delivery.domain.DeliveryService;
import com.elice.bookstore.orderbook.domain.OrderBookService;
import com.elice.bookstore.user.domain.User;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
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

  @BeforeEach
  void setUp() {

    User user = new User();
    user.setId(222L);

    Cart cart = new Cart();
    cart.setId(777L);

    Order order = new Order();
    order.setId(111L);
    order.setUser(user);
    order.setCart(cart);
    order.setOrderDate(LocalDate.now());
    order.setOrderStatus(OrderStatus.NEW);
    order.setPaymentDate(LocalDate.now());
    order.setTotalPrice(20000);
    order.setIsExist(false);


  }

  @Test
  @DisplayName("주문 저장 엔드포인트 테스트")
  void saveOrderEndpointTest() throws Exception {
    // given

    // when, then
    ResultActions result = mockMvc.perform(put("/orders/order"));
    result.andExpect(status().isCreated());

  }
}
