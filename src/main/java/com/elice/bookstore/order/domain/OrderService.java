package com.elice.bookstore.order.domain;

import com.elice.bookstore.order.domain.dto.OrderMapper;
import com.elice.bookstore.order.domain.dto.RequestOrder;
import com.elice.bookstore.order.domain.dto.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  public ResponseOrder save(RequestOrder requestOrder) {
    Order order = orderMapper.requestOrderToOrder(requestOrder);
    Order savedOrder = orderRepository.save(order);
    return orderMapper.orderToResponseOrder(savedOrder);
  }

//  public ResponseOrder save(RequestOrder requestOrder) {
//    Order order = new Order(
//        requestOrder.userId(),
//        requestOrder.cartId(),
//        requestOrder.orderStatus(),
//        requestOrder.totalPrice()
//    );
//    Order savedOrder = orderRepository.save(order);
//    return new ResponseOrder(
//        savedOrder.getUser().getId(),
//        savedOrder.getCart().getId(),
//        savedOrder.getOrderStatus(),
//        savedOrder.getTotalPrice()
//    );
//  }

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
