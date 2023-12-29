package com.wellnetworks.wellcore.java.domain.partner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPartnerPermissionGroupEntity is a Querydsl query type for WellPartnerPermissionGroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPartnerPermissionGroupEntity extends EntityPathBase<WellPartnerPermissionGroupEntity> {

    private static final long serialVersionUID = 561346352L;

    public static final QWellPartnerPermissionGroupEntity wellPartnerPermissionGroupEntity = new QWellPartnerPermissionGroupEntity("wellPartnerPermissionGroupEntity");

    public final StringPath department = createString("department");

    public final StringPath partnerManagerDescription = createString("partnerManagerDescription");

    public final StringPath partnerManagerGroupKey = createString("partnerManagerGroupKey");

    public final DateTimePath<java.time.LocalDateTime> partnerManagerModifyDate = createDateTime("partnerManagerModifyDate", java.time.LocalDateTime.class);

    public final StringPath partnerManagerPermissions = createString("partnerManagerPermissions");

    public final DateTimePath<java.time.LocalDateTime> partnerManagerRegisterDate = createDateTime("partnerManagerRegisterDate", java.time.LocalDateTime.class);

    public final ListPath<WellPartnerUserEntity, QWellPartnerUserEntity> partnerUsers = this.<WellPartnerUserEntity, QWellPartnerUserEntity>createList("partnerUsers", WellPartnerUserEntity.class, QWellPartnerUserEntity.class, PathInits.DIRECT2);

    public QWellPartnerPermissionGroupEntity(String variable) {
        super(WellPartnerPermissionGroupEntity.class, forVariable(variable));
    }

    public QWellPartnerPermissionGroupEntity(Path<? extends WellPartnerPermissionGroupEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellPartnerPermissionGroupEntity(PathMetadata metadata) {
        super(WellPartnerPermissionGroupEntity.class, metadata);
    }

}

