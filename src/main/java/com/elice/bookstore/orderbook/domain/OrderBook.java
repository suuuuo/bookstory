package com.elice.bookstore.orderbook.domain;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.config.audit.BaseTimeEntity;
import com.elice.bookstore.order.domain.Order;
import com.elice.bookstore.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * orderBook domain. (주문내역 확인)
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBook extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private int count;

//    @Column
//    private int price;

}
