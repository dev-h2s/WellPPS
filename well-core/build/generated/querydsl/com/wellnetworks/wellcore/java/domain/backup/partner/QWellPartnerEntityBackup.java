package com.wellnetworks.wellcore.java.domain.backup.partner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWellPartnerEntityBackup is a Querydsl query type for WellPartnerEntityBackup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellPartnerEntityBackup extends EntityPathBase<WellPartnerEntityBackup> {

    private static final long serialVersionUID = 266484286L;

    public static final QWellPartnerEntityBackup wellPartnerEntityBackup = new QWellPartnerEntityBackup("wellPartnerEntityBackup");

    public final StringPath apiKeyInIdx = createString("apiKeyInIdx");

    public final StringPath ceoName = createString("ceoName");

    public final StringPath ceoTelephone = createString("ceoTelephone");

    public final StringPath commisionType = createString("commisionType");

    public final StringPath commissionBankHolder = createString("commissionBankHolder");

    public final StringPath commissionBankName = createString("commissionBankName");

    public final StringPath commissionDepositAccount = createString("commissionDepositAccount");

    public final StringPath discountCategory = createString("discountCategory");

    public final StringPath emailAddress = createString("emailAddress");

    public final StringPath event = createString("event");

    public final StringPath fileIdx = createString("fileIdx");

    public final BooleanPath inApiFlag = createBoolean("inApiFlag");

    public final StringPath locationAddress = createString("locationAddress");

    public final StringPath locationDetailAddress = createString("locationDetailAddress");

    public final BooleanPath openingFlag = createBoolean("openingFlag");

    public final StringPath openingInfoIdx = createString("openingInfoIdx");

    public final StringPath openingNote = createString("openingNote");

    public final StringPath openingProgress = createString("openingProgress");

    public final DateTimePath<java.time.LocalDateTime> openingVisitDecideDate = createDateTime("openingVisitDecideDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> openingVisitRequestDate = createDateTime("openingVisitRequestDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> page = createNumber("page", Integer.class);

    public final StringPath partnerCode = createString("partnerCode");

    public final NumberPath<Long> partnerGroupId = createNumber("partnerGroupId", Long.class);

    public final NumberPath<Long> partnerId = createNumber("partnerId", Long.class);

    public final StringPath partnerIdx = createString("partnerIdx");

    public final StringPath partnerMemo = createString("partnerMemo");

    public final StringPath partnerName = createString("partnerName");

    public final StringPath partnerTelephone = createString("partnerTelephone");

    public final StringPath partnerType = createString("partnerType");

    public final StringPath partnerUpperIdx = createString("partnerUpperIdx");

    public final StringPath password = createString("password");

    public final StringPath preApprovalNumber = createString("preApprovalNumber");

    public final DateTimePath<java.time.LocalDateTime> productModifyDate = createDateTime("productModifyDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> productRegisterDate = createDateTime("productRegisterDate", java.time.LocalDateTime.class);

    public final StringPath region = createString("region");

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

    public final StringPath virtualAccountIdx = createString("virtualAccountIdx");

    public final StringPath writer = createString("writer");

    public QWellPartnerEntityBackup(String variable) {
        super(WellPartnerEntityBackup.class, forVariable(variable));
    }

    public QWellPartnerEntityBackup(Path<? extends WellPartnerEntityBackup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellPartnerEntityBackup(PathMetadata metadata) {
        super(WellPartnerEntityBackup.class, metadata);
    }

}

