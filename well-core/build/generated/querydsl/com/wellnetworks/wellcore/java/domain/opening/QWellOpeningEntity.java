package com.wellnetworks.wellcore.java.domain.opening;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellOpeningEntity is a Querydsl query type for WellOpeningEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellOpeningEntity extends EntityPathBase<WellOpeningEntity> {

    private static final long serialVersionUID = -1664016026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellOpeningEntity wellOpeningEntity = new QWellOpeningEntity("wellOpeningEntity");

    public final BooleanPath autoChargeFlag = createBoolean("autoChargeFlag");

    public final StringPath country = createString("country");

    public final StringPath countyType = createString("countyType");

    public final StringPath deviceModel = createString("deviceModel");

    public final com.wellnetworks.wellcore.java.domain.partner.QWellFakeRegistrationEntity fakeRegistration;

    public final BooleanPath inspectionFlag = createBoolean("inspectionFlag");

    public final StringPath inspector = createString("inspector");

    public final StringPath memo = createString("memo");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath openingHistoryType = createString("openingHistoryType");

    public final NumberPath<Long> openingInfoId = createNumber("openingInfoId", Long.class);

    public final StringPath openingInfoIdx = createString("openingInfoIdx");

    public final StringPath openingPhoneNumber = createString("openingPhoneNumber");

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partner;

    public final StringPath partnerName = createString("partnerName");

    public final BooleanPath passportExistence = createBoolean("passportExistence");

    public final StringPath paymentType = createString("paymentType");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final StringPath salesRepresentative = createString("salesRepresentative");

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public final StringPath writeType = createString("writeType");

    public QWellOpeningEntity(String variable) {
        this(WellOpeningEntity.class, forVariable(variable), INITS);
    }

    public QWellOpeningEntity(Path<? extends WellOpeningEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellOpeningEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellOpeningEntity(PathMetadata metadata, PathInits inits) {
        this(WellOpeningEntity.class, metadata, inits);
    }

    public QWellOpeningEntity(Class<? extends WellOpeningEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fakeRegistration = inits.isInitialized("fakeRegistration") ? new com.wellnetworks.wellcore.java.domain.partner.QWellFakeRegistrationEntity(forProperty("fakeRegistration"), inits.get("fakeRegistration")) : null;
        this.partner = inits.isInitialized("partner") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity(forProperty("partner"), inits.get("partner")) : null;
    }

}

