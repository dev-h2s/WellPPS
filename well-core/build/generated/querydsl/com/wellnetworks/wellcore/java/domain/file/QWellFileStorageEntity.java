package com.wellnetworks.wellcore.java.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellFileStorageEntity is a Querydsl query type for WellFileStorageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellFileStorageEntity extends EntityPathBase<WellFileStorageEntity> {

    private static final long serialVersionUID = -34940333L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellFileStorageEntity wellFileStorageEntity = new QWellFileStorageEntity("wellFileStorageEntity");

    public final QWellPartnerFIleStorageEntity boardFile;

    public final StringPath contentType = createString("contentType");

    public final StringPath extension = createString("extension");

    public final StringPath fileKind = createString("fileKind");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originFileName = createString("originFileName");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath savedFileName = createString("savedFileName");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    public final StringPath uploadDir = createString("uploadDir");

    public QWellFileStorageEntity(String variable) {
        this(WellFileStorageEntity.class, forVariable(variable), INITS);
    }

    public QWellFileStorageEntity(Path<? extends WellFileStorageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellFileStorageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellFileStorageEntity(PathMetadata metadata, PathInits inits) {
        this(WellFileStorageEntity.class, metadata, inits);
    }

    public QWellFileStorageEntity(Class<? extends WellFileStorageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardFile = inits.isInitialized("boardFile") ? new QWellPartnerFIleStorageEntity(forProperty("boardFile"), inits.get("boardFile")) : null;
    }

}

