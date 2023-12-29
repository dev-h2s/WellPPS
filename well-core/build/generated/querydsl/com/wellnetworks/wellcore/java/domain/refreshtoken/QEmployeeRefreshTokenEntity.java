package com.wellnetworks.wellcore.java.domain.refreshtoken;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeeRefreshTokenEntity is a Querydsl query type for EmployeeRefreshTokenEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployeeRefreshTokenEntity extends EntityPathBase<EmployeeRefreshTokenEntity> {

    private static final long serialVersionUID = 542504430L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployeeRefreshTokenEntity employeeRefreshTokenEntity = new QEmployeeRefreshTokenEntity("employeeRefreshTokenEntity");

    public final com.wellnetworks.wellcore.java.domain.employee.QWellEmployeeUserEntity employeeUser;

    public final DateTimePath<java.util.Date> expiryDate = createDateTime("expiryDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    public QEmployeeRefreshTokenEntity(String variable) {
        this(EmployeeRefreshTokenEntity.class, forVariable(variable), INITS);
    }

    public QEmployeeRefreshTokenEntity(Path<? extends EmployeeRefreshTokenEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployeeRefreshTokenEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployeeRefreshTokenEntity(PathMetadata metadata, PathInits inits) {
        this(EmployeeRefreshTokenEntity.class, metadata, inits);
    }

    public QEmployeeRefreshTokenEntity(Class<? extends EmployeeRefreshTokenEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employeeUser = inits.isInitialized("employeeUser") ? new com.wellnetworks.wellcore.java.domain.employee.QWellEmployeeUserEntity(forProperty("employeeUser"), inits.get("employeeUser")) : null;
    }

}

