package com.wellnetworks.wellcore.java.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellVirtualAccountEntity is a Querydsl query type for WellVirtualAccountEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellVirtualAccountEntity extends EntityPathBase<WellVirtualAccountEntity> {

    private static final long serialVersionUID = 1390191493L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellVirtualAccountEntity wellVirtualAccountEntity = new QWellVirtualAccountEntity("wellVirtualAccountEntity");

    public final QWellDipositEntity deposit;

    public final StringPath issuance = createString("issuance");

    public final DateTimePath<java.time.LocalDateTime> issueDate = createDateTime("issueDate", java.time.LocalDateTime.class);

    public final StringPath memo = createString("memo");

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    public final DatePath<java.time.LocalDate> registerDate = createDate("registerDate", java.time.LocalDate.class);

    public final StringPath virtualAccount = createString("virtualAccount");

    public final StringPath virtualAccountIdx = createString("virtualAccountIdx");

    public final StringPath virtualBankHolder = createString("virtualBankHolder");

    public final StringPath virtualBankName = createString("virtualBankName");

    public final StringPath writer = createString("writer");

    public QWellVirtualAccountEntity(String variable) {
        this(WellVirtualAccountEntity.class, forVariable(variable), INITS);
    }

    public QWellVirtualAccountEntity(Path<? extends WellVirtualAccountEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellVirtualAccountEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellVirtualAccountEntity(PathMetadata metadata, PathInits inits) {
        this(WellVirtualAccountEntity.class, metadata, inits);
    }

    public QWellVirtualAccountEntity(Class<? extends WellVirtualAccountEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deposit = inits.isInitialized("deposit") ? new QWellDipositEntity(forProperty("deposit"), inits.get("deposit")) : null;
        this.partner = inits.isInitialized("partner") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity(forProperty("partner"), inits.get("partner")) : null;
    }

}

