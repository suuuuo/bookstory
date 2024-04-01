package com.elice.bookstore.orderbook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderBook is a Querydsl query type for OrderBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderBook extends EntityPathBase<OrderBook> {

    private static final long serialVersionUID = -718308815L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderBook orderBook = new QOrderBook("orderBook");

    public final com.elice.bookstore.book.domain.QBook book;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.elice.bookstore.order.domain.QOrder order;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QOrderBook(String variable) {
        this(OrderBook.class, forVariable(variable), INITS);
    }

    public QOrderBook(Path<? extends OrderBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderBook(PathMetadata metadata, PathInits inits) {
        this(OrderBook.class, metadata, inits);
    }

    public QOrderBook(Class<? extends OrderBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.elice.bookstore.book.domain.QBook(forProperty("book"), inits.get("book")) : null;
        this.order = inits.isInitialized("order") ? new com.elice.bookstore.order.domain.QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

