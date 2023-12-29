package com.wellnetworks.wellcore.java.domain.apikeyIn;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWellApikeyIssueEntity is a Querydsl query type for WellApikeyIssueEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellApikeyIssueEntity extends EntityPathBase<WellApikeyIssueEntity> {

    private static final long serialVersionUID = 731949206L;

    public static final QWellApikeyIssueEntity wellApikeyIssueEntity = new QWellApikeyIssueEntity("wellApikeyIssueEntity");

    public final StringPath apiKeyIn = createString("apiKeyIn");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QWellApikeyIssueEntity(String variable) {
        super(WellApikeyIssueEntity.class, forVariable(variable));
    }

    public QWellApikeyIssueEntity(Path<? extends WellApikeyIssueEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellApikeyIssueEntity(PathMetadata metadata) {
        super(WellApikeyIssueEntity.class, metadata);
    }

}

