package com.wellnetworks.wellcore.java.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellProductSearchRequestEntity is a Querydsl query type for WellProductSearchRequestEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellProductSearchRequestEntity extends EntityPathBase<WellProductSearchRequestEntity> {

    private static final long serialVersionUID = -1053518215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellProductSearchRequestEntity wellProductSearchRequestEntity = new QWellProductSearchRequestEntity("wellProductSearchRequestEntity");

    public final QWellProductSearchEntity _super;

    //inherited
    public final BooleanPath chargeFlag;

    //inherited
    public final NumberPath<Integer> maxChargeCount;

    // inherited
    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    // inherited
    public final QWellProductEntity product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> productNamSearchRequestDate;

    //inherited
    public final StringPath prSearchIdx;

    //inherited
    public final StringPath requestBrowser;

    //inherited
    public final StringPath requestDevice;

    //inherited
    public final StringPath requestIp;

    //inherited
    public final StringPath requestSystem;

    //inherited
    public final BooleanPath responseFlag;

    //inherited
    public final BooleanPath searchFlag;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> searchProcessingDate;

    public final StringPath searchRequestDateApikey = createString("searchRequestDateApikey");

    public final StringPath searchRequestTel = createString("searchRequestTel");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> searchResponseDate;

    public QWellProductSearchRequestEntity(String variable) {
        this(WellProductSearchRequestEntity.class, forVariable(variable), INITS);
    }

    public QWellProductSearchRequestEntity(Path<? extends WellProductSearchRequestEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellProductSearchRequestEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellProductSearchRequestEntity(PathMetadata metadata, PathInits inits) {
        this(WellProductSearchRequestEntity.class, metadata, inits);
    }

    public QWellProductSearchRequestEntity(Class<? extends WellProductSearchRequestEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QWellProductSearchEntity(type, metadata, inits);
        this.chargeFlag = _super.chargeFlag;
        this.maxChargeCount = _super.maxChargeCount;
        this.partner = _super.partner;
        this.product = _super.product;
        this.productNamSearchRequestDate = _super.productNamSearchRequestDate;
        this.prSearchIdx = _super.prSearchIdx;
        this.requestBrowser = _super.requestBrowser;
        this.requestDevice = _super.requestDevice;
        this.requestIp = _super.requestIp;
        this.requestSystem = _super.requestSystem;
        this.responseFlag = _super.responseFlag;
        this.searchFlag = _super.searchFlag;
        this.searchProcessingDate = _super.searchProcessingDate;
        this.searchResponseDate = _super.searchResponseDate;
    }

}

