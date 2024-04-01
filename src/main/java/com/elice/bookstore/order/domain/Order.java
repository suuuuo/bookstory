package com.elice.bookstore.order.domain;

import com.elice.bookstore.config.audit.BaseEntity;
import com.elice.bookstore.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

/**
 * order domain.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @CreationTimestamp // sysdate
    private LocalDate orderDate;

//    @Column //자동생성
//    private String orderNumber;

    @Enumerated(EnumType.STRING) // 알아보자
    private OrderStatus orderStatus;

    @Column
    @CreationTimestamp //sysdate
    private LocalDate paymentDate;

    @Column
    private int totalPrice;

    @ColumnDefault("0")
    private Boolean isExist;
}

