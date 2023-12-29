package com.wellnetworks.wellcore.java.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellEmployeeFileStorageEntity is a Querydsl query type for WellEmployeeFileStorageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellEmployeeFileStorageEntity extends EntityPathBase<WellEmployeeFileStorageEntity> {

    private static final long serialVersionUID = -1867914171L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellEmployeeFileStorageEntity wellEmployeeFileStorageEntity = new QWellEmployeeFileStorageEntity("wellEmployeeFileStorageEntity");

    public final StringPath employeeIdx = createString("employeeIdx");

    public final QWellFileStorageEntity file;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QWellEmployeeFileStorageEntity(String variable) {
        this(WellEmployeeFileStorageEntity.class, forVariable(variable), INITS);
    }

    public QWellEmployeeFileStorageEntity(Path<? extends WellEmployeeFileStorageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellEmployeeFileStorageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellEmployeeFileStorageEntity(PathMetadata metadata, PathInits inits) {
        this(WellEmployeeFileStorageEntity.class, metadata, inits);
    }

    public QWellEmployeeFileStorageEntity(Class<? extends WellEmployeeFileStorageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new QWellFileStorageEntity(forProperty("file"), inits.get("file")) : null;
    }

}

