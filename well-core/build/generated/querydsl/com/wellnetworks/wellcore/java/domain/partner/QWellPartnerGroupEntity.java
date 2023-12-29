package com.wellnetworks.wellcore.java.domain.partner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPartnerGroupEntity is a Querydsl query type for WellPartnerGroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPartnerGroupEntity extends EntityPathBase<WellPartnerGroupEntity> {

    private static final long serialVersionUID = -561646401L;

    public static final QWellPartnerGroupEntity wellPartnerGroupEntity = new QWellPartnerGroupEntity("wellPartnerGroupEntity");

    public final NumberPath<Long> partnerGroupId = createNumber("partnerGroupId", Long.class);

    public final StringPath PartnerGroupName = createString("PartnerGroupName");

    public final ListPath<WellPartnerEntity, QWellPartnerEntity> partners = this.<WellPartnerEntity, QWellPartnerEntity>createList("partners", WellPartnerEntity.class, QWellPartnerEntity.class, PathInits.DIRECT2);

    public QWellPartnerGroupEntity(String variable) {
        super(WellPartnerGroupEntity.class, forVariable(variable));
    }

    public QWellPartnerGroupEntity(Path<? extends WellPartnerGroupEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellPartnerGroupEntity(PathMetadata metadata) {
        super(WellPartnerGroupEntity.class, metadata);
    }

}

