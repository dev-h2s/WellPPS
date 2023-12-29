package com.wellnetworks.wellcore.java.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellProductSearchEntity is a Querydsl query type for WellProductSearchEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellProductSearchEntity extends EntityPathBase<WellProductSearchEntity> {

    private static final long serialVersionUID = -31598948L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellProductSearchEntity wellProductSearchEntity = new QWellProductSearchEntity("wellProductSearchEntity");

    public final BooleanPath chargeFlag = createBoolean("chargeFlag");

    public final NumberPath<Integer> maxChargeCount = createNumber("maxChargeCount", Integer.class);

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    public final QWellProductEntity product;

    public final DateTimePath<java.time.LocalDateTime> productNamSearchRequestDate = createDateTime("productNamSearchRequestDate", java.time.LocalDateTime.class);

    public final StringPath prSearchIdx = createString("prSearchIdx");

    public final StringPath requestBrowser = createString("requestBrowser");

    public final StringPath requestDevice = createString("requestDevice");

    public final StringPath requestIp = createString("requestIp");

    public final StringPath requestSystem = createString("requestSystem");

    public final BooleanPath responseFlag = createBoolean("responseFlag");

    public final BooleanPath searchFlag = createBoolean("searchFlag");

    public final DateTimePath<java.time.LocalDateTime> searchProcessingDate = createDateTime("searchProcessingDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> searchResponseDate = createDateTime("searchResponseDate", java.time.LocalDateTime.class);

    public QWellProductSearchEntity(String variable) {
        this(WellProductSearchEntity.class, forVariable(variable), INITS);
    }

    public QWellProductSearchEntity(Path<? extends WellProductSearchEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellProductSearchEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellProductSearchEntity(PathMetadata metadata, PathInits inits) {
        this(WellProductSearchEntity.class, metadata, inits);
    }

    public QWellProductSearchEntity(Class<? extends WellProductSearchEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity(forProperty("partner"), inits.get("partner")) : null;
        this.product = inits.isInitialized("product") ? new QWellProductEntity(forProperty("product"), inits.get("product")) : null;
    }

}

