package com.wellnetworks.wellcore.java.domain.permission.personal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPersonalDropDownEntity is a Querydsl query type for WellPersonalDropDownEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPersonalDropDownEntity extends EntityPathBase<WellPersonalDropDownEntity> {

    private static final long serialVersionUID = 635357218L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellPersonalDropDownEntity wellPersonalDropDownEntity = new QWellPersonalDropDownEntity("wellPersonalDropDownEntity");

    public final StringPath dropName = createString("dropName");

    public final com.wellnetworks.wellcore.java.domain.employee.QWellEmployeeEntity employee;

    public final NumberPath<Long> perDropId = createNumber("perDropId", Long.class);

    public QWellPersonalDropDownEntity(String variable) {
        this(WellPersonalDropDownEntity.class, forVariable(variable), INITS);
    }

    public QWellPersonalDropDownEntity(Path<? extends WellPersonalDropDownEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellPersonalDropDownEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellPersonalDropDownEntity(PathMetadata metadata, PathInits inits) {
        this(WellPersonalDropDownEntity.class, metadata, inits);
    }

    public QWellPersonalDropDownEntity(Class<? extends WellPersonalDropDownEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new com.wellnetworks.wellcore.java.domain.employee.QWellEmployeeEntity(forProperty("employee"), inits.get("employee")) : null;
    }

}

