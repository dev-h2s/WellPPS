package com.wellnetworks.wellcore.java.domain.charge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellChargeCommissionEntity is a Querydsl query type for WellChargeCommissionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellChargeCommissionEntity extends EntityPathBase<WellChargeCommissionEntity> {

    private static final long serialVersionUID = 1059543545L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellChargeCommissionEntity wellChargeCommissionEntity = new QWellChargeCommissionEntity("wellChargeCommissionEntity");

    public final NumberPath<Float> apiDiscount = createNumber("apiDiscount", Float.class);

    public final NumberPath<Integer> cash = createNumber("cash", Integer.class);

    public final NumberPath<Float> chargeDiscount = createNumber("chargeDiscount", Float.class);

    public final NumberPath<Long> CommissionId = createNumber("CommissionId", Long.class);

    public final NumberPath<Float> incomeTaxInfo = createNumber("incomeTaxInfo", Float.class);

    public final NumberPath<Float> openingDiscount = createNumber("openingDiscount", Float.class);

    public final NumberPath<Float> otherDiscount = createNumber("otherDiscount", Float.class);

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    public final NumberPath<Float> phoneIssueDiscount = createNumber("phoneIssueDiscount", Float.class);

    public final NumberPath<Integer> pps = createNumber("pps", Integer.class);

    public final NumberPath<Float> vatInfo = createNumber("vatInfo", Float.class);

    public QWellChargeCommissionEntity(String variable) {
        this(WellChargeCommissionEntity.class, forVariable(variable), INITS);
    }

    public QWellChargeCommissionEntity(Path<? extends WellChargeCommissionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellChargeCommissionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellChargeCommissionEntity(PathMetadata metadata, PathInits inits) {
        this(WellChargeCommissionEntity.class, metadata, inits);
    }

    public QWellChargeCommissionEntity(Class<? extends WellChargeCommissionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity(forProperty("partner"), inits.get("partner")) : null;
    }

}

