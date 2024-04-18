package com.elice.bookstore.delivery.domain;

import com.elice.bookstore.delivery.domain.dto.DeliveryMapper;
import com.elice.bookstore.delivery.domain.dto.RequestDelivery;
import com.elice.bookstore.delivery.domain.dto.ResponseDelivery;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryMapper deliveryMapper;

  public void updateDeliveryDetailsById(Long orderId, RequestDelivery requestDelivery) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findByOrderId(orderId);
    if (optionalDelivery.isEmpty()) {
      throw new EntityNotFoundException("Delivery not found with id: " + orderId);
    }

    Delivery delivery = optionalDelivery.get();

    // 매개변수로 전달된 값으로 엔티티 수정
    if (requestDelivery.address() != null) {
      delivery.setAddress(requestDelivery.address());
    }
    if (requestDelivery.receiverName() != null) {
      delivery.setReceiverName(requestDelivery.receiverName());
    }
    if (requestDelivery.receiverPhoneNumber() != null) {
      delivery.setReceiverPhoneNumber(requestDelivery.receiverPhoneNumber());
    }
    if (requestDelivery.request() != null) {
      delivery.setRequest(requestDelivery.request());
    }

    // 수정된 엔티티 저장
    deliveryRepository.save(delivery);
  }

  public ResponseDelivery saveOrderDetails(RequestDelivery requestDelivery) {
    Delivery delivery = deliveryMapper.requestDeliveryToDelivery(requestDelivery);
    Delivery saveDelivery = deliveryRepository.save(delivery);
    return deliveryMapper.toResponseDelivery(saveDelivery);
  }
}
