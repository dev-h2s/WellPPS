package com.wellnetworks.wellcore.java.domain.opening;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellCommissionOpeningPolicyEntity is a Querydsl query type for WellCommissionOpeningPolicyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellCommissionOpeningPolicyEntity extends EntityPathBase<WellCommissionOpeningPolicyEntity> {

    private static final long serialVersionUID = -1012061619L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellCommissionOpeningPolicyEntity wellCommissionOpeningPolicyEntity = new QWellCommissionOpeningPolicyEntity("wellCommissionOpeningPolicyEntity");

    public final StringPath passportType = createString("passportType");

    public final com.wellnetworks.wellcore.java.domain.product.QWellProductEntity product;

    public final StringPath registerDate = createString("registerDate");

    public final StringPath versionId = createString("versionId");

    public QWellCommissionOpeningPolicyEntity(String variable) {
        this(WellCommissionOpeningPolicyEntity.class, forVariable(variable), INITS);
    }

    public QWellCommissionOpeningPolicyEntity(Path<? extends WellCommissionOpeningPolicyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellCommissionOpeningPolicyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellCommissionOpeningPolicyEntity(PathMetadata metadata, PathInits inits) {
        this(WellCommissionOpeningPolicyEntity.class, metadata, inits);
    }

    public QWellCommissionOpeningPolicyEntity(Class<? extends WellCommissionOpeningPolicyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.wellnetworks.wellcore.java.domain.product.QWellProductEntity(forProperty("product"), inits.get("product")) : null;
    }

}

