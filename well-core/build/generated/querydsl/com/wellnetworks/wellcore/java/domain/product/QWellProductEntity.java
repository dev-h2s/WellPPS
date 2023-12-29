package com.wellnetworks.wellcore.java.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellProductEntity is a Querydsl query type for WellProductEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellProductEntity extends EntityPathBase<WellProductEntity> {

    private static final long serialVersionUID = 1120512916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellProductEntity wellProductEntity = new QWellProductEntity("wellProductEntity");

    public final NumberPath<Integer> baseFee = createNumber("baseFee", Integer.class);

    public final StringPath data = createString("data");

    public final StringPath etc = createString("etc");

    public final StringPath externalCode = createString("externalCode");

    public final StringPath internalCode = createString("internalCode");

    public final StringPath memo = createString("memo");

    public final StringPath mvnoProductName = createString("mvnoProductName");

    public final StringPath network = createString("network");

    public final BooleanPath openingHistorySearchFlag = createBoolean("openingHistorySearchFlag");

    public final ListPath<com.wellnetworks.wellcore.java.domain.opening.WellCommissionOpeningPolicyEntity, com.wellnetworks.wellcore.java.domain.opening.QWellCommissionOpeningPolicyEntity> OpeningPolicy = this.<com.wellnetworks.wellcore.java.domain.opening.WellCommissionOpeningPolicyEntity, com.wellnetworks.wellcore.java.domain.opening.QWellCommissionOpeningPolicyEntity>createList("OpeningPolicy", com.wellnetworks.wellcore.java.domain.opening.WellCommissionOpeningPolicyEntity.class, com.wellnetworks.wellcore.java.domain.opening.QWellCommissionOpeningPolicyEntity.class, PathInits.DIRECT2);

    public final com.wellnetworks.wellcore.java.domain.operator.QWellOperatorEntity operator;

    public final StringPath productIdx = createString("productIdx");

    public final StringPath productName = createString("productName");

    public final ListPath<WellProductSearchEntity, QWellProductSearchEntity> productSearch = this.<WellProductSearchEntity, QWellProductSearchEntity>createList("productSearch", WellProductSearchEntity.class, QWellProductSearchEntity.class, PathInits.DIRECT2);

    public final StringPath productType = createString("productType");

    public final StringPath sms = createString("sms");

    public final BooleanPath visibleFlag = createBoolean("visibleFlag");

    public final StringPath voice = createString("voice");

    public QWellProductEntity(String variable) {
        this(WellProductEntity.class, forVariable(variable), INITS);
    }

    public QWellProductEntity(Path<? extends WellProductEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellProductEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellProductEntity(PathMetadata metadata, PathInits inits) {
        this(WellProductEntity.class, metadata, inits);
    }

    public QWellProductEntity(Class<? extends WellProductEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.operator = inits.isInitialized("operator") ? new com.wellnetworks.wellcore.java.domain.operator.QWellOperatorEntity(forProperty("operator")) : null;
    }

}

