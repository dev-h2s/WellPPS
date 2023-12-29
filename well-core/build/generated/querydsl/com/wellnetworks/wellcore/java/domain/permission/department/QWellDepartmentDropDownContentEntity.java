package com.wellnetworks.wellcore.java.domain.permission.department;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellDepartmentDropDownContentEntity is a Querydsl query type for WellDepartmentDropDownContentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellDepartmentDropDownContentEntity extends EntityPathBase<WellDepartmentDropDownContentEntity> {

    private static final long serialVersionUID = -485223715L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellDepartmentDropDownContentEntity wellDepartmentDropDownContentEntity = new QWellDepartmentDropDownContentEntity("wellDepartmentDropDownContentEntity");

    public final QWellDepartmentDropDownEntity departmentDropdown;

    public final NumberPath<Long> depDropContentId = createNumber("depDropContentId", Long.class);

    public final NumberPath<Long> employeeId = createNumber("employeeId", Long.class);

    public QWellDepartmentDropDownContentEntity(String variable) {
        this(WellDepartmentDropDownContentEntity.class, forVariable(variable), INITS);
    }

    public QWellDepartmentDropDownContentEntity(Path<? extends WellDepartmentDropDownContentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellDepartmentDropDownContentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellDepartmentDropDownContentEntity(PathMetadata metadata, PathInits inits) {
        this(WellDepartmentDropDownContentEntity.class, metadata, inits);
    }

    public QWellDepartmentDropDownContentEntity(Class<? extends WellDepartmentDropDownContentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.departmentDropdown = inits.isInitialized("departmentDropdown") ? new QWellDepartmentDropDownEntity(forProperty("departmentDropdown")) : null;
    }

}

