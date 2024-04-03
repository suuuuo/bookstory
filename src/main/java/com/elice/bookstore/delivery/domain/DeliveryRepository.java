package com.elice.bookstore.delivery.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
  /* 배송 전 delivery 테이블에 요청사항 수정 */
//    @Transactional
//    @Modifying
//    @Query
//    void update

}
