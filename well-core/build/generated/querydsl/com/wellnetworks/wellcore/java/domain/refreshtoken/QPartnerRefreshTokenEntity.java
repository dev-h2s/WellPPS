package com.wellnetworks.wellcore.java.domain.refreshtoken;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPartnerRefreshTokenEntity is a Querydsl query type for PartnerRefreshTokenEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartnerRefreshTokenEntity extends EntityPathBase<PartnerRefreshTokenEntity> {

    private static final long serialVersionUID = -1445240022L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPartnerRefreshTokenEntity partnerRefreshTokenEntity = new QPartnerRefreshTokenEntity("partnerRefreshTokenEntity");

    public final DateTimePath<java.util.Date> expiryDate = createDateTime("expiryDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerUserEntity partnerUser;

    public final StringPath refreshToken = createString("refreshToken");

    public QPartnerRefreshTokenEntity(String variable) {
        this(PartnerRefreshTokenEntity.class, forVariable(variable), INITS);
    }

    public QPartnerRefreshTokenEntity(Path<? extends PartnerRefreshTokenEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPartnerRefreshTokenEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPartnerRefreshTokenEntity(PathMetadata metadata, PathInits inits) {
        this(PartnerRefreshTokenEntity.class, metadata, inits);
    }

    public QPartnerRefreshTokenEntity(Class<? extends PartnerRefreshTokenEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partnerUser = inits.isInitialized("partnerUser") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerUserEntity(forProperty("partnerUser"), inits.get("partnerUser")) : null;
    }

}

