package com.elice.bookstore.delivery.domain;

import com.elice.bookstore.delivery.domain.dto.RequestDelivery;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 DeliveryRepository
 //ToDo : 회원 CRUD
          수정 - 사용자는 주문 완료 후 배송 전까지 주문 정보를 수정할 수 있다. (Delivery)
*/
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

  /* 회원 : 배송 전, 주문 정보 수정 */
  Optional<Delivery> findById(Long orderId);
  Delivery save(Delivery delivery);
}
