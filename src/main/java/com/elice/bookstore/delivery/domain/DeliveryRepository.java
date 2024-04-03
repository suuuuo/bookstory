package com.elice.bookstore.delivery.domain;

import java.util.Map;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
  DeliveryRepository
  //ToDo : 회원 CRUD
           수정 - 사용자는 주문 완료 후 배송 전까지 주문 정보를 수정할 수 있다. (Delivery)
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

  /* 회원 : 배송 전, 요청사항 수정 */
  @Transactional
  @Modifying
  @Query("UPDATE Delivery d SET " +
      "d.address = COALESCE(:#{#params['address']}, d.address), " +
      "d.receiverName = COALESCE(:#{#params['receiverName']}, d.receiverName), " +
      "d.receiverPhoneNumber = COALESCE(:#{#params['receiverPhoneNumber']}, d.receiverPhoneNumber), " +
      "d.request = COALESCE(:#{#params['request']}, d.request) " +
      "WHERE d.id = :id")
  void updateDeliveryDetailsById(@Param("params") Map<String, String> params, @Param("id") Long id);

}
