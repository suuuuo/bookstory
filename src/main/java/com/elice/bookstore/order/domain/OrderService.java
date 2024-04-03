package com.elice.bookstore.order.domain;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order save(Order order) {
      return orderRepository.save(order);
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
