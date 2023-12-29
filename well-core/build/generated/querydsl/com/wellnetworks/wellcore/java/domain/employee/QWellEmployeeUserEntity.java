package com.wellnetworks.wellcore.java.domain.employee;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellEmployeeUserEntity is a Querydsl query type for WellEmployeeUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellEmployeeUserEntity extends EntityPathBase<WellEmployeeUserEntity> {

    private static final long serialVersionUID = -365078055L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellEmployeeUserEntity wellEmployeeUserEntity = new QWellEmployeeUserEntity("wellEmployeeUserEntity");

    public final CollectionPath<org.springframework.security.core.GrantedAuthority, SimplePath<org.springframework.security.core.GrantedAuthority>> authorities = this.<org.springframework.security.core.GrantedAuthority, SimplePath<org.springframework.security.core.GrantedAuthority>>createCollection("authorities", org.springframework.security.core.GrantedAuthority.class, SimplePath.class, PathInits.DIRECT2);

    public final QWellEmployeeEntity employee;

    public final QWellEmployeeEntity employeeEntity;

    public final StringPath employeeIdentification = createString("employeeIdentification");

    public final StringPath employeeIdx = createString("employeeIdx");

    public final QWellEmployeeManagerGroupEntity employeeManagerGroupKey;

    public final DateTimePath<java.time.LocalDateTime> employeeUserModifyDate = createDateTime("employeeUserModifyDate", java.time.LocalDateTime.class);

    public final StringPath employeeUserPwd = createString("employeeUserPwd");

    public final DateTimePath<java.time.LocalDateTime> employeeUserRegisterDate = createDateTime("employeeUserRegisterDate", java.time.LocalDateTime.class);

    public final StringPath groupKey = createString("groupKey");

    public final StringPath groupPermissionKey = createString("groupPermissionKey");

    public final BooleanPath isFirstLogin = createBoolean("isFirstLogin");

    public final BooleanPath isPasswordResetRequired = createBoolean("isPasswordResetRequired");

    public final BooleanPath isPhoneVerified = createBoolean("isPhoneVerified");

    public final QWellEmployeeManagerGroupEntity managerGroupEntity;

    public final StringPath permissions = createString("permissions");

    public final NumberPath<Integer> phoneVerificationAttempts = createNumber("phoneVerificationAttempts", Integer.class);

    public final StringPath phoneVerificationCode = createString("phoneVerificationCode");

    public final DateTimePath<java.time.LocalDateTime> phoneVerificationExpiration = createDateTime("phoneVerificationExpiration", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> phoneVerificationSentTime = createDateTime("phoneVerificationSentTime", java.time.LocalDateTime.class);

    public final com.wellnetworks.wellcore.java.domain.refreshtoken.QEmployeeRefreshTokenEntity refreshToken;

    public final StringPath tmpPwd = createString("tmpPwd");

    public final NumberPath<Integer> tmpPwdCount = createNumber("tmpPwdCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> tmpPwdDate = createDateTime("tmpPwdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> tmpPwdExpiration = createDateTime("tmpPwdExpiration", java.time.LocalDateTime.class);

    public QWellEmployeeUserEntity(String variable) {
        this(WellEmployeeUserEntity.class, forVariable(variable), INITS);
    }

    public QWellEmployeeUserEntity(Path<? extends WellEmployeeUserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellEmployeeUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellEmployeeUserEntity(PathMetadata metadata, PathInits inits) {
        this(WellEmployeeUserEntity.class, metadata, inits);
    }

    public QWellEmployeeUserEntity(Class<? extends WellEmployeeUserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QWellEmployeeEntity(forProperty("employee"), inits.get("employee")) : null;
        this.employeeEntity = inits.isInitialized("employeeEntity") ? new QWellEmployeeEntity(forProperty("employeeEntity"), inits.get("employeeEntity")) : null;
        this.employeeManagerGroupKey = inits.isInitialized("employeeManagerGroupKey") ? new QWellEmployeeManagerGroupEntity(forProperty("employeeManagerGroupKey")) : null;
        this.managerGroupEntity = inits.isInitialized("managerGroupEntity") ? new QWellEmployeeManagerGroupEntity(forProperty("managerGroupEntity")) : null;
        this.refreshToken = inits.isInitialized("refreshToken") ? new com.wellnetworks.wellcore.java.domain.refreshtoken.QEmployeeRefreshTokenEntity(forProperty("refreshToken"), inits.get("refreshToken")) : null;
    }

}

