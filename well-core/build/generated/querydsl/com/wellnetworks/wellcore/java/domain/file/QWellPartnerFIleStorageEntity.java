package com.wellnetworks.wellcore.java.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPartnerFIleStorageEntity is a Querydsl query type for WellPartnerFIleStorageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPartnerFIleStorageEntity extends EntityPathBase<WellPartnerFIleStorageEntity> {

    private static final long serialVersionUID = -1606473463L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellPartnerFIleStorageEntity wellPartnerFIleStorageEntity = new QWellPartnerFIleStorageEntity("wellPartnerFIleStorageEntity");

    public final QWellFileStorageEntity file;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath partnerIdx = createString("partnerIdx");

    public QWellPartnerFIleStorageEntity(String variable) {
        this(WellPartnerFIleStorageEntity.class, forVariable(variable), INITS);
    }

    public QWellPartnerFIleStorageEntity(Path<? extends WellPartnerFIleStorageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellPartnerFIleStorageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellPartnerFIleStorageEntity(PathMetadata metadata, PathInits inits) {
        this(WellPartnerFIleStorageEntity.class, metadata, inits);
    }

    public QWellPartnerFIleStorageEntity(Class<? extends WellPartnerFIleStorageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new QWellFileStorageEntity(forProperty("file"), inits.get("file")) : null;
    }

}

