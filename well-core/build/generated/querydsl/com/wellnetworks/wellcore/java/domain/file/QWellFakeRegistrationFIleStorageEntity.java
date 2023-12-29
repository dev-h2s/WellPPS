package com.wellnetworks.wellcore.java.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellFakeRegistrationFIleStorageEntity is a Querydsl query type for WellFakeRegistrationFIleStorageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellFakeRegistrationFIleStorageEntity extends EntityPathBase<WellFakeRegistrationFIleStorageEntity> {

    private static final long serialVersionUID = 616583525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellFakeRegistrationFIleStorageEntity wellFakeRegistrationFIleStorageEntity = new QWellFakeRegistrationFIleStorageEntity("wellFakeRegistrationFIleStorageEntity");

    public final com.wellnetworks.wellcore.java.domain.partner.QWellFakeRegistrationEntity fakeRegistration;

    public final QWellFileStorageEntity file;

    public final StringPath fileIdx = createString("fileIdx");

    public QWellFakeRegistrationFIleStorageEntity(String variable) {
        this(WellFakeRegistrationFIleStorageEntity.class, forVariable(variable), INITS);
    }

    public QWellFakeRegistrationFIleStorageEntity(Path<? extends WellFakeRegistrationFIleStorageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellFakeRegistrationFIleStorageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellFakeRegistrationFIleStorageEntity(PathMetadata metadata, PathInits inits) {
        this(WellFakeRegistrationFIleStorageEntity.class, metadata, inits);
    }

    public QWellFakeRegistrationFIleStorageEntity(Class<? extends WellFakeRegistrationFIleStorageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fakeRegistration = inits.isInitialized("fakeRegistration") ? new com.wellnetworks.wellcore.java.domain.partner.QWellFakeRegistrationEntity(forProperty("fakeRegistration"), inits.get("fakeRegistration")) : null;
        this.file = inits.isInitialized("file") ? new QWellFileStorageEntity(forProperty("file"), inits.get("file")) : null;
    }

}

