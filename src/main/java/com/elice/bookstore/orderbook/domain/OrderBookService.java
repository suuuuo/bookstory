package com.elice.bookstore.orderbook.domain;

import com.elice.bookstore.order.domain.OrderRepository;
import com.elice.bookstore.order.domain.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderBookService {

  private final OrderBookRepository orderBookRepository;

  /* **** 회원 CRUD *****/
  /* 나의 주문 내역 조회 */
  public Page<OrderBook> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable) {
    return orderBookRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable);
  }

  /* **** 관리자 CRUD *****/
  /* 전체 주문 내역 조회 */
  public Page<OrderBook> findAllByOrderByCreatedAtDesc(Pageable pageable) {
    return orderBookRepository.findAllByOrderByCreatedAtDesc(pageable);
  }
}
