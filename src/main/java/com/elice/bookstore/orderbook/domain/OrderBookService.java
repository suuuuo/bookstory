package com.elice.bookstore.orderbook.domain;

import com.elice.bookstore.orderbook.domain.dto.OrderBookDTO;
import com.elice.bookstore.orderbook.domain.dto.OrderBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderBookService {

  private final OrderBookRepository orderBookRepository;
  private final OrderBookMapper orderBookMapper;

  /* **** 회원 CRUD *****/
  /* 나의 주문 내역 조회 */
  public Page<OrderBookDTO> getAllMyOrders(Long userId, Pageable pageable) {
    return orderBookRepository.findOrderBooksByUserIdOrderByCreatedAtDesc(userId, pageable);
  }

  /* **** 관리자 CRUD *****/
  /* 전체 주문 내역 조회 */
  public Page<OrderBookDTO> findAllByOrderByCreatedAtDesc(Pageable pageable) {
    Page<OrderBook> orderBooks = orderBookRepository.findAllByOrderByCreatedAtDesc(pageable);
    return orderBooks.map(orderBookMapper::toOrderBookDTO);
  }
}
