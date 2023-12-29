package com.wellnetworks.wellcore.java.domain.charge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellChargeGuaranteeEntity is a Querydsl query type for WellChargeGuaranteeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellChargeGuaranteeEntity extends EntityPathBase<WellChargeGuaranteeEntity> {

    private static final long serialVersionUID = -667547168L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellChargeGuaranteeEntity wellChargeGuaranteeEntity = new QWellChargeGuaranteeEntity("wellChargeGuaranteeEntity");

    public final QWellChargeHistoryEntity _super;

    //inherited
    public final StringPath chargeBrowser;

    //inherited
    public final StringPath chargeDevice;

    public final QWellChargeHistoryEntity chargeHistory;

    //inherited
    public final StringPath chargeHistoryIdx;

    //inherited
    public final StringPath chargeSystem;

    //inherited
    public final NumberPath<Integer> chargeTryPrice;

    public final NumberPath<Integer> guaranteeDiposit = createNumber("guaranteeDiposit", Integer.class);

    public final NumberPath<Integer> judgmentValue = createNumber("judgmentValue", Integer.class);

    // inherited
    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    //inherited
    public final StringPath productSearchIdx;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate;

    //inherited
    public final StringPath seq;

    public QWellChargeGuaranteeEntity(String variable) {
        this(WellChargeGuaranteeEntity.class, forVariable(variable), INITS);
    }

    public QWellChargeGuaranteeEntity(Path<? extends WellChargeGuaranteeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellChargeGuaranteeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellChargeGuaranteeEntity(PathMetadata metadata, PathInits inits) {
        this(WellChargeGuaranteeEntity.class, metadata, inits);
    }

    public QWellChargeGuaranteeEntity(Class<? extends WellChargeGuaranteeEntity> type, PathMetadata metadata, PathInits inits) {
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

