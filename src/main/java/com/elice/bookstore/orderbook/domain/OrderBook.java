package com.elice.bookstore.orderbook.domain;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.order.domain.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * orderBook domain.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private int count;

    @Column
    private int price;
}
