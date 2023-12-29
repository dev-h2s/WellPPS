package com.wellnetworks.wellcore.java.domain.permission.personal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPersonalMenuPermissionEntity is a Querydsl query type for WellPersonalMenuPermissionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPersonalMenuPermissionEntity extends EntityPathBase<WellPersonalMenuPermissionEntity> {

    private static final long serialVersionUID = 508799039L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellPersonalMenuPermissionEntity wellPersonalMenuPermissionEntity = new QWellPersonalMenuPermissionEntity("wellPersonalMenuPermissionEntity");

    public final BooleanPath deleteFlag = createBoolean("deleteFlag");

    public final QWellPersonalDropContentEntity dropdownContent;

    public final BooleanPath editFlag = createBoolean("editFlag");

    public final BooleanPath excelFlag = createBoolean("excelFlag");

    public final BooleanPath inputFlag = createBoolean("inputFlag");

    public final NumberPath<Long> perMenuId = createNumber("perMenuId", Long.class);

    public final BooleanPath searchFlag = createBoolean("searchFlag");

    public final BooleanPath viewFlag = createBoolean("viewFlag");

    public QWellPersonalMenuPermissionEntity(String variable) {
        this(WellPersonalMenuPermissionEntity.class, forVariable(variable), INITS);
    }

    public QWellPersonalMenuPermissionEntity(Path<? extends WellPersonalMenuPermissionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellPersonalMenuPermissionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellPersonalMenuPermissionEntity(PathMetadata metadata, PathInits inits) {
        this(WellPersonalMenuPermissionEntity.class, metadata, inits);
    }

    public QWellPersonalMenuPermissionEntity(Class<? extends WellPersonalMenuPermissionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dropdownContent = inits.isInitialized("dropdownContent") ? new QWellPersonalDropContentEntity(forProperty("dropdownContent"), inits.get("dropdownContent")) : null;
    }

}

