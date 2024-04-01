package com.elice.bookstore.order.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    public final OrderService orderService;

    /* **** 회원 CRUD *****/
    /* 나의 주문 내역 조회 */
    /* orders 테이블에 데이터 입력 (장바구니에 담은것 가져와서 주문) */
    /* 배송 전 orders 테이블에 요청사항 수정 */
    /* 배송 전 주문 취소 */


    /* **** 관리자 CRUD *****/
    /* 전체 주문 내역 */
    @GetMapping("/admin/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrder();
        return ResponseEntity.ok().body(orders);
    }

    /* 주문 상태 변경 (결제 완료 / 배송 준비 등) */
    @PutMapping("/admin/orders/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @PathVariable("orderStatus") OrderStatus orderStatus) {
        orderService.updateOrderStateById(orderStatus, id);
        return ResponseEntity.ok().build();
    }


    /* 주문 내역 삭제(delete로 구현) */
    @DeleteMapping("/admin/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}