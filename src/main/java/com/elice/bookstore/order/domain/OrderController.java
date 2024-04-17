package com.elice.bookstore.order.domain;

import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.delivery.domain.DeliveryService;
import com.elice.bookstore.delivery.domain.dto.RequestDelivery;
import com.elice.bookstore.delivery.domain.dto.ResponseDelivery;
import com.elice.bookstore.order.domain.dto.RequestOrder;
import com.elice.bookstore.order.domain.dto.RequestOrderStatusUpdate;
import com.elice.bookstore.order.domain.dto.ResponseOrder;
import com.elice.bookstore.orderbook.domain.OrderBookService;
import com.elice.bookstore.orderbook.domain.dto.OrderBookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController("/api")
@RequiredArgsConstructor
public class OrderController {

  public final OrderService orderService;
  public final OrderBookService orderBookService;
  public final DeliveryService deliveryService;
  private final JwtUtil jwtUtil;
  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

  /* **** 회원 CRUD *****/
  /* 회원 : 나의 주문 내역 조회 */
  @GetMapping("v1/orders")
  public ResponseEntity<Page<OrderBookDTO>> getAllMyOrders(
      @RequestHeader("Authorization") String access,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "15") int size) {
    Long id = Long.valueOf(jwtUtil.getId(access));

    Page<OrderBookDTO> allMyOrders =
        orderBookService.getAllMyOrders(id, PageRequest.of(page, size));
    logger.info(">>> id: {}, {}", id, allMyOrders.getContent());
    return ResponseEntity.ok().body(allMyOrders);
  }

  /* 회원 : 주문하기 (장바구니에 담은것 가져와서 주문) */
  @PostMapping("/v1/orders/order")
  public ResponseEntity<String> saveOrder(
      @RequestHeader("Authorization") String access, @RequestBody RequestOrder requestOrder) {
    String id = jwtUtil.getId(access);
    String role = jwtUtil.getRole(access);
    logger.info(">>> id: {} , role: {}", id, role);

    ResponseOrder savedOrder = orderService.save(requestOrder, access);
    return ResponseEntity.status(HttpStatus.CREATED).body("성공 : " + savedOrder);
  }

  /* 회원 : 주문 상세정보 저장 */
  @PostMapping("/v1/orders/order/{id}")
  public ResponseEntity<String> saveOrderDetails(
      @PathVariable Long id,
      @RequestHeader("Authorization") String access,
      @RequestBody RequestDelivery requestDelivery) {
    String role = jwtUtil.getRole(access);
    logger.info(">>> orderId : {}, role: {}, body : {}", id, role, requestDelivery);

    ResponseDelivery saveOrderDetails = deliveryService.saveOrderDetails(requestDelivery);
    return ResponseEntity.status(HttpStatus.CREATED).body("성공 : " + saveOrderDetails);
  }

  /* 회원 : 배송 전, 주문 정보 수정 */
  @PutMapping("/v1/orders/update/{id}")
  public ResponseEntity<String> updateOrder(
      @PathVariable Long id,
      @RequestHeader("Authorization") String access,
      @RequestBody RequestDelivery requestDelivery) {
    String role = jwtUtil.getRole(access);
    logger.info(">>> orderId : {}, role: {}, body : {}", id, role, requestDelivery);

    deliveryService.updateDeliveryDetailsById(id, requestDelivery);
    return ResponseEntity.ok("배송 정보가 변경되었습니다.");
  }

  /* 회원 : 배송 전 주문취소 */
  @PutMapping("/v1/orders/cancel/{id}")
  public ResponseEntity<String> cancelOrder(
      @PathVariable Long id, @RequestHeader("Authorization") String access) {
    String role = jwtUtil.getRole(access);
    logger.info(">>> role: {}", role);

    orderService.updateOrderStatusById(id);
    return ResponseEntity.ok("order cancel 성공.");
  }

  /* **** 관리자 CRUD *****/
  /* 관리자 : 전체 주문 내역 조회 */
  @GetMapping("/v1/admin/orders")
  public ResponseEntity<Page<OrderBookDTO>> adminGetAllOrders(
      @RequestHeader("Authorization") String access,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "15") int size) {
    String role = jwtUtil.getRole(access);
    logger.info(">>>> role: {}", role);

    Pageable pageable = PageRequest.of(page, size);
    Page<OrderBookDTO> allOrders = orderBookService.findAllByOrderByCreatedAtDesc(pageable);
    return ResponseEntity.ok().body(allOrders);
  }

  /* 관리자 : 주문 상태 변경 (결제 완료 / 배송 준비 등) */
  @PutMapping("/v1/admin/orders/{id}")
  public ResponseEntity<String> adminUpdateOrderStatus(
      @RequestHeader("Authorization") String access,
      @RequestBody RequestOrderStatusUpdate orderUpdate) {
    String role = jwtUtil.getRole(access);
    logger.info(">>>> role: {}", role);

    orderService.adminUpdateOrderStatueById(orderUpdate);
    return ResponseEntity.ok().body("");
  }

  /* 관리자 : 주문 내역 삭제 (delete로 구현함) */
  @DeleteMapping("/v1/admin/orders/{id}")
  public ResponseEntity<String> adminDeleteOrder(
      @PathVariable Long id, @RequestHeader("Authorization") String access) {
    String role = jwtUtil.getRole(access);
    logger.info(">>>> role: {}", role);

    orderService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
