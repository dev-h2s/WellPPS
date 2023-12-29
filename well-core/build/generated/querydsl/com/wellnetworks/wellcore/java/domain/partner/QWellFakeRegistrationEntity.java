package com.wellnetworks.wellcore.java.domain.partner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellFakeRegistrationEntity is a Querydsl query type for WellFakeRegistrationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellFakeRegistrationEntity extends EntityPathBase<WellFakeRegistrationEntity> {

    private static final long serialVersionUID = 97542934L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellFakeRegistrationEntity wellFakeRegistrationEntity = new QWellFakeRegistrationEntity("wellFakeRegistrationEntity");

    public final NumberPath<Long> fakeRegistrationId = createNumber("fakeRegistrationId", Long.class);

    public final StringPath fakeType = createString("fakeType");

    public final ListPath<com.wellnetworks.wellcore.java.domain.file.WellFakeRegistrationFIleStorageEntity, com.wellnetworks.wellcore.java.domain.file.QWellFakeRegistrationFIleStorageEntity> files = this.<com.wellnetworks.wellcore.java.domain.file.WellFakeRegistrationFIleStorageEntity, com.wellnetworks.wellcore.java.domain.file.QWellFakeRegistrationFIleStorageEntity>createList("files", com.wellnetworks.wellcore.java.domain.file.WellFakeRegistrationFIleStorageEntity.class, com.wellnetworks.wellcore.java.domain.file.QWellFakeRegistrationFIleStorageEntity.class, PathInits.DIRECT2);

    public final StringPath modifier = createString("modifier");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath note = createString("note");

    public final com.wellnetworks.wellcore.java.domain.opening.QWellOpeningEntity openingInfo;

    public final DateTimePath<java.time.LocalDateTime> terminationDate = createDateTime("terminationDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> uploadDate = createDateTime("uploadDate", java.time.LocalDateTime.class);

    public final StringPath uploader = createString("uploader");

    public QWellFakeRegistrationEntity(String variable) {
        this(WellFakeRegistrationEntity.class, forVariable(variable), INITS);
    }

    public QWellFakeRegistrationEntity(Path<? extends WellFakeRegistrationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellFakeRegistrationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellFakeRegistrationEntity(PathMetadata metadata, PathInits inits) {
        this(WellFakeRegistrationEntity.class, metadata, inits);
    }

    public QWellFakeRegistrationEntity(Class<? extends WellFakeRegistrationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.openingInfo = inits.isInitialized("openingInfo") ? new com.wellnetworks.wellcore.java.domain.opening.QWellOpeningEntity(forProperty("openingInfo"), inits.get("openingInfo")) : null;
    }

}

