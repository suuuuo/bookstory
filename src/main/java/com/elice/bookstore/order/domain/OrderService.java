package com.elice.bookstore.order.domain;

import com.elice.bookstore.order.domain.dto.RequestOrder;
import com.elice.bookstore.order.domain.dto.ResponseOrder;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  @Autowired
  private final OrderRepository orderRepository;

  public ResponseOrder save(RequestOrder requestOrder) {
    Order order = new Order(
        requestOrder.user(),
        requestOrder.cart(),
        requestOrder.orderStatus(),
        requestOrder.totalPrice()
    );
    Order savedOrder = orderRepository.save(order);
    return new ResponseOrder(
        savedOrder.getUser(),
        savedOrder.getCart(),
        savedOrder.getOrderStatus(),
        savedOrder.getTotalPrice()
    );
  }

  public void updateOrderStatusById(Long id) {
    orderRepository.updateOrderStatusById(id);
  }

  public void adminUpdateOrderStatueById(OrderStatus orderStatus, Long id) {
    orderRepository.updateOrderStatusById(orderStatus, id);
  }

  public void deleteById(Long id) {
    orderRepository.deleteById(id);
  }

}
