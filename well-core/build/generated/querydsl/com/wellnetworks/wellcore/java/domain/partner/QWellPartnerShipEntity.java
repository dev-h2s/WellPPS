package com.wellnetworks.wellcore.java.domain.partner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWellPartnerShipEntity is a Querydsl query type for WellPartnerShipEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPartnerShipEntity extends EntityPathBase<WellPartnerShipEntity> {

    private static final long serialVersionUID = -666230750L;

    public static final QWellPartnerShipEntity wellPartnerShipEntity = new QWellPartnerShipEntity("wellPartnerShipEntity");

    public final BooleanPath agreement = createBoolean("agreement");

    public final StringPath email = createString("email");

    public final StringPath note = createString("note");

    public final NumberPath<Integer> partnershipId = createNumber("partnershipId", Integer.class);

    public final StringPath partnershipName = createString("partnershipName");

    public final StringPath salesManager = createString("salesManager");

    public final StringPath tel = createString("tel");

    public QWellPartnerShipEntity(String variable) {
        super(WellPartnerShipEntity.class, forVariable(variable));
    }

    public QWellPartnerShipEntity(Path<? extends WellPartnerShipEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellPartnerShipEntity(PathMetadata metadata) {
        super(WellPartnerShipEntity.class, metadata);
    }

}

