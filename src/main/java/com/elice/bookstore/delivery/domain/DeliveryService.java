package com.elice.bookstore.delivery.domain;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;

  public void updateDeliveryDetailsById(Map<String, String> params, Long orderId) {
    deliveryRepository.updateDeliveryDetailsById(params, orderId);
  }


}
