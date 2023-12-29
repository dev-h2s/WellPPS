package com.wellnetworks.wellcore.java.domain.partner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellPartnerEntity is a Querydsl query type for WellPartnerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPartnerEntity extends EntityPathBase<WellPartnerEntity> {

    private static final long serialVersionUID = -2114985914L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellPartnerEntity wellPartnerEntity = new QWellPartnerEntity("wellPartnerEntity");

    public final com.wellnetworks.wellcore.java.domain.apikeyIn.QWellApikeyInEntity apiKey;

    public final StringPath ceoName = createString("ceoName");

    public final StringPath ceoTelephone = createString("ceoTelephone");

    public final StringPath commisionType = createString("commisionType");

    public final StringPath commissionBankHolder = createString("commissionBankHolder");

    public final StringPath commissionBankName = createString("commissionBankName");

    public final StringPath commissionDepositAccount = createString("commissionDepositAccount");

    public final StringPath discountCategory = createString("discountCategory");

    public final StringPath emailAddress = createString("emailAddress");

    public final StringPath event = createString("event");

    public final BooleanPath inApiFlag = createBoolean("inApiFlag");

    public final StringPath locationAddress = createString("locationAddress");

    public final StringPath locationDetailAddress = createString("locationDetailAddress");

    public final BooleanPath openingFlag = createBoolean("openingFlag");

    public final StringPath openingNote = createString("openingNote");

    public final StringPath openingProgress = createString("openingProgress");

    public final DateTimePath<java.time.LocalDateTime> openingVisitDecideDate = createDateTime("openingVisitDecideDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> openingVisitRequestDate = createDateTime("openingVisitRequestDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> page = createNumber("page", Integer.class);

    public final StringPath partnerCode = createString("partnerCode");

    public final QWellPartnerGroupEntity partnerGroup;

    public final StringPath partnerIdx = createString("partnerIdx");

    public final StringPath partnerMemo = createString("partnerMemo");

    public final StringPath partnerName = createString("partnerName");

    public final StringPath partnerTelephone = createString("partnerTelephone");

    public final StringPath partnerType = createString("partnerType");

    public final StringPath partnerUpperIdx = createString("partnerUpperIdx");

    public final QWellPartnerUserEntity partnerUser;

    public final StringPath password = createString("password");

    public final StringPath preApprovalNumber = createString("preApprovalNumber");

    public final DateTimePath<java.time.LocalDateTime> productModifyDate = createDateTime("productModifyDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> productRegisterDate = createDateTime("productRegisterDate", java.time.LocalDateTime.class);

    public final StringPath registrationAddress = createString("registrationAddress");

    public final StringPath registrationDetailAddress = createString("registrationDetailAddress");

    public final StringPath registrationNumber = createString("registrationNumber");

    public final StringPath salesManager = createString("salesManager");

    public final DateTimePath<java.time.LocalDateTime> salesTeamVisitDate = createDateTime("salesTeamVisitDate", java.time.LocalDateTime.class);

    public final StringPath salesTeamVisitMemo = createString("salesTeamVisitMemo");

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public final BooleanPath specialPolicyCharge = createBoolean("specialPolicyCharge");

    public final BooleanPath specialPolicyOpening = createBoolean("specialPolicyOpening");

    public final DateTimePath<java.time.LocalDateTime> subscriptionDate = createDateTime("subscriptionDate", java.time.LocalDateTime.class);

    public final StringPath transactionStatus = createString("transactionStatus");

    public final com.wellnetworks.wellcore.java.domain.account.QWellVirtualAccountEntity virtualAccount;

    public final StringPath writer = createString("writer");

    public QWellPartnerEntity(String variable) {
        this(WellPartnerEntity.class, forVariable(variable), INITS);
    }

    public QWellPartnerEntity(Path<? extends WellPartnerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellPartnerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellPartnerEntity(PathMetadata metadata, PathInits inits) {
        this(WellPartnerEntity.class, metadata, inits);
    }

    public QWellPartnerEntity(Class<? extends WellPartnerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.apiKey = inits.isInitialized("apiKey") ? new com.wellnetworks.wellcore.java.domain.apikeyIn.QWellApikeyInEntity(forProperty("apiKey"), inits.get("apiKey")) : null;
        this.partnerGroup = inits.isInitialized("partnerGroup") ? new QWellPartnerGroupEntity(forProperty("partnerGroup")) : null;
        this.partnerUser = inits.isInitialized("partnerUser") ? new QWellPartnerUserEntity(forProperty("partnerUser"), inits.get("partnerUser")) : null;
        this.virtualAccount = inits.isInitialized("virtualAccount") ? new com.wellnetworks.wellcore.java.domain.account.QWellVirtualAccountEntity(forProperty("virtualAccount"), inits.get("virtualAccount")) : null;
    }

}

