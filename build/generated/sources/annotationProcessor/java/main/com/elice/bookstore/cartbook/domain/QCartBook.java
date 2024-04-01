package com.elice.bookstore.cartbook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartBook is a Querydsl query type for CartBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartBook extends EntityPathBase<CartBook> {

    private static final long serialVersionUID = 583738203L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartBook cartBook = new QCartBook("cartBook");

    public final com.elice.bookstore.config.audit.QBaseEntity _super = new com.elice.bookstore.config.audit.QBaseEntity(this);

    public final com.elice.bookstore.book.domain.QBook book;

    public final com.elice.bookstore.cart.domain.QCart cart;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCartBook(String variable) {
        this(CartBook.class, forVariable(variable), INITS);
    }

    public QCartBook(Path<? extends CartBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartBook(PathMetadata metadata, PathInits inits) {
        this(CartBook.class, metadata, inits);
    }

    public QCartBook(Class<? extends CartBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.elice.bookstore.book.domain.QBook(forProperty("book"), inits.get("book")) : null;
        this.cart = inits.isInitialized("cart") ? new com.elice.bookstore.cart.domain.QCart(forProperty("cart"), inits.get("cart")) : null;
    }

}

