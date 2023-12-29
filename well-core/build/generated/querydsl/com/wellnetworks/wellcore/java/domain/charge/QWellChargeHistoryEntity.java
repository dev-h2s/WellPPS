package com.wellnetworks.wellcore.java.domain.charge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellChargeHistoryEntity is a Querydsl query type for WellChargeHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellChargeHistoryEntity extends EntityPathBase<WellChargeHistoryEntity> {

    private static final long serialVersionUID = -181090260L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellChargeHistoryEntity wellChargeHistoryEntity = new QWellChargeHistoryEntity("wellChargeHistoryEntity");

    public final StringPath chargeBrowser = createString("chargeBrowser");

    public final StringPath chargeDevice = createString("chargeDevice");

    public final StringPath chargeHistoryIdx = createString("chargeHistoryIdx");

    public final StringPath chargeSystem = createString("chargeSystem");

    public final NumberPath<Integer> chargeTryPrice = createNumber("chargeTryPrice", Integer.class);

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    public final StringPath productSearchIdx = createString("productSearchIdx");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final StringPath seq = createString("seq");

    public QWellChargeHistoryEntity(String variable) {
        this(WellChargeHistoryEntity.class, forVariable(variable), INITS);
    }

    public QWellChargeHistoryEntity(Path<? extends WellChargeHistoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellChargeHistoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellChargeHistoryEntity(PathMetadata metadata, PathInits inits) {
        this(WellChargeHistoryEntity.class, metadata, inits);
    }

    public QWellChargeHistoryEntity(Class<? extends WellChargeHistoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity(forProperty("partner"), inits.get("partner")) : null;
    }

}

