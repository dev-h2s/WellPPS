package com.wellnetworks.wellcore.java.domain.charge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellChargeRequestEntity is a Querydsl query type for WellChargeRequestEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellChargeRequestEntity extends EntityPathBase<WellChargeRequestEntity> {

    private static final long serialVersionUID = -2034094617L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellChargeRequestEntity wellChargeRequestEntity = new QWellChargeRequestEntity("wellChargeRequestEntity");

    public final QWellChargeHistoryEntity _super;

    //inherited
    public final StringPath chargeBrowser;

    //inherited
    public final StringPath chargeDevice;

    public final QWellChargeHistoryEntity chargeHistory;

    //inherited
    public final StringPath chargeHistoryIdx;

    public final NumberPath<Integer> chargeRequestPrice = createNumber("chargeRequestPrice", Integer.class);

    public final StringPath chargeRequestTel = createString("chargeRequestTel");

    //inherited
    public final StringPath chargeSystem;

    //inherited
    public final NumberPath<Integer> chargeTryPrice;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    // inherited
    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    //inherited
    public final StringPath productSearchIdx;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate;

    public final StringPath requestApiKey = createString("requestApiKey");

    //inherited
    public final StringPath seq;

    public QWellChargeRequestEntity(String variable) {
        this(WellChargeRequestEntity.class, forVariable(variable), INITS);
    }

    public QWellChargeRequestEntity(Path<? extends WellChargeRequestEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellChargeRequestEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellChargeRequestEntity(PathMetadata metadata, PathInits inits) {
        this(WellChargeRequestEntity.class, metadata, inits);
    }

    public QWellChargeRequestEntity(Class<? extends WellChargeRequestEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QWellChargeHistoryEntity(type, metadata, inits);
        this.chargeBrowser = _super.chargeBrowser;
        this.chargeDevice = _super.chargeDevice;
        this.chargeHistory = inits.isInitialized("chargeHistory") ? new QWellChargeHistoryEntity(forProperty("chargeHistory"), inits.get("chargeHistory")) : null;
        this.chargeHistoryIdx = _super.chargeHistoryIdx;
        this.chargeSystem = _super.chargeSystem;
        this.chargeTryPrice = _super.chargeTryPrice;
        this.partner = _super.partner;
        this.productSearchIdx = _super.productSearchIdx;
        this.registerDate = _super.registerDate;
        this.seq = _super.seq;
    }

}

