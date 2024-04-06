package com.elice.bookstore.orderbook.domain;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/*
  OrderBookRepository
  //ToDo : 회원 CRUD
           조회 - 회원은 마이페이지에서 자신의 주문 내역을 조회할 수 있다. (OrderBook)
           관리자 CRUD
           조회 - 관리자는 관리자 페이지에서 모든 회원들의 주문 내역을 조회할 수 있다. (OrderBook)
 */
@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {

  /* **** 회원 CRUD *****/
  /* 나의 주문 내역 조회 */
  Page<OrderBook> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

  /* **** 관리자 CRUD *****/
  /* 전체 주문 내역 조회 */
  Page<OrderBook> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
