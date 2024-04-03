package com.elice.bookstore.order.domain;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * order status sample data.
 */
public enum OrderStatus {
    NEW,
    PENDING,
    CANCELLED

//    PAID,
//    PREPARING,
//    SHIPPED,
//    DELIVERED,
//    REFUND_REQUEST,
//    EXCHANGE_REQUEST,
//    REFUND_COMPLETED,
//    EXCHANGE_COMPLETED

}
