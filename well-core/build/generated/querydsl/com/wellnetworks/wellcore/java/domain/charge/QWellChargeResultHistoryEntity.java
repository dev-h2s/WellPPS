package com.wellnetworks.wellcore.java.domain.charge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellChargeResultHistoryEntity is a Querydsl query type for WellChargeResultHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellChargeResultHistoryEntity extends EntityPathBase<WellChargeResultHistoryEntity> {

    private static final long serialVersionUID = 1046872911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellChargeResultHistoryEntity wellChargeResultHistoryEntity = new QWellChargeResultHistoryEntity("wellChargeResultHistoryEntity");

    public final QWellChargeHistoryEntity _super;

    public final StringPath callbackRequest = createString("callbackRequest");

    //inherited
    public final StringPath chargeBrowser;

    //inherited
    public final StringPath chargeDevice;

    public final DateTimePath<java.time.LocalDateTime> chargeEndDate = createDateTime("chargeEndDate", java.time.LocalDateTime.class);

    public final QWellChargeHistoryEntity chargeHistory;

    //inherited
    public final StringPath chargeHistoryIdx;

    public final NumberPath<Integer> chargePrice = createNumber("chargePrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> chargeProcessingDate = createDateTime("chargeProcessingDate", java.time.LocalDateTime.class);

    public final StringPath chargeResult = createString("chargeResult");

    public final DateTimePath<java.time.LocalDateTime> chargeStartDt = createDateTime("chargeStartDt", java.time.LocalDateTime.class);

    //inherited
    public final StringPath chargeSystem;

    //inherited
    public final NumberPath<Integer> chargeTryPrice;

    public final NumberPath<Integer> completedChargeCount = createNumber("completedChargeCount", Integer.class);

    public final StringPath memo = createString("memo");

    public final StringPath modifier = createString("modifier");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath note = createString("note");

    // inherited
    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    //inherited
    public final StringPath productSearchIdx;

    public final StringPath progress = createString("progress");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate;

    public final NumberPath<Integer> requestChargeCount = createNumber("requestChargeCount", Integer.class);

    //inherited
    public final StringPath seq;

    public QWellChargeResultHistoryEntity(String variable) {
        this(WellChargeResultHistoryEntity.class, forVariable(variable), INITS);
    }

    public QWellChargeResultHistoryEntity(Path<? extends WellChargeResultHistoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellChargeResultHistoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellChargeResultHistoryEntity(PathMetadata metadata, PathInits inits) {
        this(WellChargeResultHistoryEntity.class, metadata, inits);
    }

    public QWellChargeResultHistoryEntity(Class<? extends WellChargeResultHistoryEntity> type, PathMetadata metadata, PathInits inits) {
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

