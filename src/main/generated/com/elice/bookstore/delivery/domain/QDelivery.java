package com.elice.bookstore.delivery.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = -1919654639L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final com.elice.bookstore.config.audit.QBaseEntity _super = new com.elice.bookstore.config.audit.QBaseEntity(this);

    public final StringPath address = createString("address");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DatePath<java.time.LocalDate> deliveredDate = createDate("deliveredDate", java.time.LocalDate.class);

    public final StringPath deliveryStatus = createString("deliveryStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isExist = createBoolean("isExist");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final com.elice.bookstore.order.domain.QOrder order;

    public final StringPath receiverName = createString("receiverName");

    public final StringPath receiverPhoneNumber = createString("receiverPhoneNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath request = createString("request");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QDelivery(String variable) {
        this(Delivery.class, forVariable(variable), INITS);
    }

    public QDelivery(Path<? extends Delivery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDelivery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDelivery(PathMetadata metadata, PathInits inits) {
        this(Delivery.class, metadata, inits);
    }

    public QDelivery(Class<? extends Delivery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.elice.bookstore.order.domain.QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

