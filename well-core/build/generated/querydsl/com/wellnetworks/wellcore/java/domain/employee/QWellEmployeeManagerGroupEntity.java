package com.wellnetworks.wellcore.java.domain.employee;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellEmployeeManagerGroupEntity is a Querydsl query type for WellEmployeeManagerGroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellEmployeeManagerGroupEntity extends EntityPathBase<WellEmployeeManagerGroupEntity> {

    private static final long serialVersionUID = 711068832L;

    public static final QWellEmployeeManagerGroupEntity wellEmployeeManagerGroupEntity = new QWellEmployeeManagerGroupEntity("wellEmployeeManagerGroupEntity");

    public final StringPath department = createString("department");

    public final ListPath<WellEmployeeUserEntity, QWellEmployeeUserEntity> employee = this.<WellEmployeeUserEntity, QWellEmployeeUserEntity>createList("employee", WellEmployeeUserEntity.class, QWellEmployeeUserEntity.class, PathInits.DIRECT2);

    public final StringPath employeeManagerDescription = createString("employeeManagerDescription");

    public final StringPath employeeManagerGroupKey = createString("employeeManagerGroupKey");

    public final DateTimePath<java.time.LocalDateTime> employeeManagerModifyDate = createDateTime("employeeManagerModifyDate", java.time.LocalDateTime.class);

    public final StringPath employeeManagerPermissions = createString("employeeManagerPermissions");

    public final DateTimePath<java.time.LocalDateTime> employeeManagerRegisterDate = createDateTime("employeeManagerRegisterDate", java.time.LocalDateTime.class);

    public QWellEmployeeManagerGroupEntity(String variable) {
        super(WellEmployeeManagerGroupEntity.class, forVariable(variable));
    }

    public QWellEmployeeManagerGroupEntity(Path<? extends WellEmployeeManagerGroupEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellEmployeeManagerGroupEntity(PathMetadata metadata) {
        super(WellEmployeeManagerGroupEntity.class, metadata);
    }

}

