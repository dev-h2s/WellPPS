package com.wellnetworks.wellcore.java.domain.permission.department;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellDepartmentMenuPermissionEntity is a Querydsl query type for WellDepartmentMenuPermissionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellDepartmentMenuPermissionEntity extends EntityPathBase<WellDepartmentMenuPermissionEntity> {

    private static final long serialVersionUID = 1278179839L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellDepartmentMenuPermissionEntity wellDepartmentMenuPermissionEntity = new QWellDepartmentMenuPermissionEntity("wellDepartmentMenuPermissionEntity");

    public final BooleanPath deleteFlag = createBoolean("deleteFlag");

    public final NumberPath<Long> depMenuId = createNumber("depMenuId", Long.class);

    public final QWellDepartmentDropDownContentEntity dropdownContent;

    public final BooleanPath editFlag = createBoolean("editFlag");

    public final BooleanPath excelFlag = createBoolean("excelFlag");

    public final BooleanPath inputFlag = createBoolean("inputFlag");

    public final BooleanPath searchFlag = createBoolean("searchFlag");

    public final BooleanPath viewFlag = createBoolean("viewFlag");

    public QWellDepartmentMenuPermissionEntity(String variable) {
        this(WellDepartmentMenuPermissionEntity.class, forVariable(variable), INITS);
    }

    public QWellDepartmentMenuPermissionEntity(Path<? extends WellDepartmentMenuPermissionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellDepartmentMenuPermissionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellDepartmentMenuPermissionEntity(PathMetadata metadata, PathInits inits) {
        this(WellDepartmentMenuPermissionEntity.class, metadata, inits);
    }

    public QWellDepartmentMenuPermissionEntity(Class<? extends WellDepartmentMenuPermissionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dropdownContent = inits.isInitialized("dropdownContent") ? new QWellDepartmentDropDownContentEntity(forProperty("dropdownContent"), inits.get("dropdownContent")) : null;
    }

}

