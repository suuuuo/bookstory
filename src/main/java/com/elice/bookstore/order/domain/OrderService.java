package com.elice.bookstore.order.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
      this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrder() {
      return orderRepository.findAll();
    }

    public void updateOrderStateById(OrderStatus orderStatus, Long id) {
      orderRepository.updateOrderStatusById(orderStatus, id);
    }

    public void deleteById(Long id) {
      orderRepository.deleteById(id);
    }

    public Order findById(Long id) {
      Optional<Order> orderOptional = orderRepository.findById(id);
      return orderOptional.orElse(null);
    }
}
