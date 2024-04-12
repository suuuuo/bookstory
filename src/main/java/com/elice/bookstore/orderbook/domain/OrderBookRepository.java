package com.elice.bookstore.orderbook.domain;

import com.elice.bookstore.orderbook.domain.dto.OrderBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
  /* 나의 주문 내역 조회 - 복합적인 데이터를 반환하기 때문에 DTO 사용 */
  @Query(
      "SELECT new com.elice.bookstore.orderbook.domain.dto.OrderBookDTO "
          + "(O.orderDate as orderDate, O.orderStatus as orderStatus, B.itemName as itemName )"
          + "FROM OrderBook OB "
          + "INNER JOIN OB.order O "
          + "INNER JOIN OB.book B "
          + "INNER JOIN OB.user U "
          + "WHERE U.id = :userId ")
  Page<OrderBookDTO> findOrderBooksByUserIdOrderByCreatedAtDesc(
      @Param("userId") Long userId, Pageable pageable);

  /* **** 관리자 CRUD *****/
  /* 전체 주문 내역 조회 */
  Page<OrderBook> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
