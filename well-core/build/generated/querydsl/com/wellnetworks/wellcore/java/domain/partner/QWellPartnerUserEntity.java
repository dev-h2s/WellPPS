package com.wellnetworks.wellcore.java.domain.partner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPartnerUserEntity is a Querydsl query type for WellPartnerUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPartnerUserEntity extends EntityPathBase<WellPartnerUserEntity> {

    private static final long serialVersionUID = -367687055L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellPartnerUserEntity wellPartnerUserEntity = new QWellPartnerUserEntity("wellPartnerUserEntity");

    public final CollectionPath<org.springframework.security.core.GrantedAuthority, SimplePath<org.springframework.security.core.GrantedAuthority>> authorities = this.<org.springframework.security.core.GrantedAuthority, SimplePath<org.springframework.security.core.GrantedAuthority>>createCollection("authorities", org.springframework.security.core.GrantedAuthority.class, SimplePath.class, PathInits.DIRECT2);

    public final StringPath groupKey = createString("groupKey");

    public final StringPath groupPermissionKey = createString("groupPermissionKey");

    public final BooleanPath isFirstLogin = createBoolean("isFirstLogin");

    public final BooleanPath isPasswordResetRequired = createBoolean("isPasswordResetRequired");

    public final BooleanPath isPhoneVerified = createBoolean("isPhoneVerified");

    public final QWellPartnerEntity partner;

    public final StringPath partnerIdentification = createString("partnerIdentification");

    public final StringPath partnerIdx = createString("partnerIdx");

    public final QWellPartnerPermissionGroupEntity partnerManagerGroupKey;

    public final DateTimePath<java.time.LocalDateTime> partnerUserModifyDate = createDateTime("partnerUserModifyDate", java.time.LocalDateTime.class);

    public final StringPath partnerUserPwd = createString("partnerUserPwd");

    public final DateTimePath<java.time.LocalDateTime> partnerUserRegisterDate = createDateTime("partnerUserRegisterDate", java.time.LocalDateTime.class);

    public final StringPath permissions = createString("permissions");

    public final NumberPath<Integer> phoneVerificationAttempts = createNumber("phoneVerificationAttempts", Integer.class);

    public final StringPath phoneVerificationCode = createString("phoneVerificationCode");

    public final DateTimePath<java.time.LocalDateTime> phoneVerificationExpiration = createDateTime("phoneVerificationExpiration", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> phoneVerificationSentTime = createDateTime("phoneVerificationSentTime", java.time.LocalDateTime.class);

    public final com.wellnetworks.wellcore.java.domain.refreshtoken.QPartnerRefreshTokenEntity refreshTokens;

    public final StringPath tmpPwd = createString("tmpPwd");

    public final NumberPath<Integer> tmpPwdCount = createNumber("tmpPwdCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> tmpPwdDate = createDateTime("tmpPwdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> tmpPwdExpiration = createDateTime("tmpPwdExpiration", java.time.LocalDateTime.class);

    public QWellPartnerUserEntity(String variable) {
        this(WellPartnerUserEntity.class, forVariable(variable), INITS);
    }

    public QWellPartnerUserEntity(Path<? extends WellPartnerUserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellPartnerUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellPartnerUserEntity(PathMetadata metadata, PathInits inits) {
        this(WellPartnerUserEntity.class, metadata, inits);
    }

    public QWellPartnerUserEntity(Class<? extends WellPartnerUserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new QWellPartnerEntity(forProperty("partner"), inits.get("partner")) : null;
        this.partnerManagerGroupKey = inits.isInitialized("partnerManagerGroupKey") ? new QWellPartnerPermissionGroupEntity(forProperty("partnerManagerGroupKey")) : null;
        this.refreshTokens = inits.isInitialized("refreshTokens") ? new com.wellnetworks.wellcore.java.domain.refreshtoken.QPartnerRefreshTokenEntity(forProperty("refreshTokens"), inits.get("refreshTokens")) : null;
    }

}

