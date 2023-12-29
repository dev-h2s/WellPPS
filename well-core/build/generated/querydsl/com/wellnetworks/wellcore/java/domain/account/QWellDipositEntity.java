package com.wellnetworks.wellcore.java.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellDipositEntity is a Querydsl query type for WellDipositEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellDipositEntity extends EntityPathBase<WellDipositEntity> {

    private static final long serialVersionUID = -391809731L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellDipositEntity wellDipositEntity = new QWellDipositEntity("wellDipositEntity");

    public final BooleanPath dipositAdjustment = createBoolean("dipositAdjustment");

    public final NumberPath<Integer> dipositAmount = createNumber("dipositAmount", Integer.class);

    public final NumberPath<Integer> dipositBalance = createNumber("dipositBalance", Integer.class);

    public final StringPath dipositIdx = createString("dipositIdx");

    public final StringPath dipositStatus = createString("dipositStatus");

    public final StringPath memo = createString("memo");

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final QWellVirtualAccountEntity virtualAccount;

    public final StringPath writer = createString("writer");

    public QWellDipositEntity(String variable) {
        this(WellDipositEntity.class, forVariable(variable), INITS);
    }

    public QWellDipositEntity(Path<? extends WellDipositEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellDipositEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellDipositEntity(PathMetadata metadata, PathInits inits) {
        this(WellDipositEntity.class, metadata, inits);
    }

    public QWellDipositEntity(Class<? extends WellDipositEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity(forProperty("partner"), inits.get("partner")) : null;
        this.virtualAccount = inits.isInitialized("virtualAccount") ? new QWellVirtualAccountEntity(forProperty("virtualAccount"), inits.get("virtualAccount")) : null;
    }

}

