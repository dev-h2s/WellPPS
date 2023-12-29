package com.wellnetworks.wellcore.java.domain.permission.personal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPersonalDropContentEntity is a Querydsl query type for WellPersonalDropContentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPersonalDropContentEntity extends EntityPathBase<WellPersonalDropContentEntity> {

    private static final long serialVersionUID = -632212577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellPersonalDropContentEntity wellPersonalDropContentEntity = new QWellPersonalDropContentEntity("wellPersonalDropContentEntity");

    public final StringPath contentName = createString("contentName");

    public final NumberPath<Long> perDropContentId = createNumber("perDropContentId", Long.class);

    public final QWellPersonalDropDownEntity personalDropDown;

    public QWellPersonalDropContentEntity(String variable) {
        this(WellPersonalDropContentEntity.class, forVariable(variable), INITS);
    }

    public QWellPersonalDropContentEntity(Path<? extends WellPersonalDropContentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellPersonalDropContentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellPersonalDropContentEntity(PathMetadata metadata, PathInits inits) {
        this(WellPersonalDropContentEntity.class, metadata, inits);
    }

    public QWellPersonalDropContentEntity(Class<? extends WellPersonalDropContentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.personalDropDown = inits.isInitialized("personalDropDown") ? new QWellPersonalDropDownEntity(forProperty("personalDropDown"), inits.get("personalDropDown")) : null;
    }

}

