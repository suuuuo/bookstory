package com.elice.bookstore.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/*
  OrderRepository
  //ToDo : 회원 CRUD
           조회 - 회원은 마이페이지에서 자신의 주문 내역을 조회할 수 있다.
           입력 - 회원은 장바구니에 담긴 상품들을 주문할 수 있다.
           수정 - 사용자는 주문 완료 후 배송 전까지 주문 정보를 수정할 수 있다.
           삭제 - 사용자는 주문 완료 후 배송 전까지 주문을 취소할 수 있다.
           관리자 CRUD
           조회 - 관리자는 관리자 페이지에서 모든 회원들의 주문 내역을 조회할 수 있다.
           수정 - 관리자는 사용자의 주문 내역에서 배송 상태를 수정할 수 있다.
           삭제 - 관리자는 관리자 페이지에서 회원들의 주문 내역을 삭제할 수 있다.
 */
    public interface OrderRepository extends JpaRepository<Order, Long> {

    }
