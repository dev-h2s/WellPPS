package com.wellnetworks.wellcore.java.domain.opening;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellCommissionPassportEntity is a Querydsl query type for WellCommissionPassportEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellCommissionPassportEntity extends EntityPathBase<WellCommissionPassportEntity> {

    private static final long serialVersionUID = 386749813L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellCommissionPassportEntity wellCommissionPassportEntity = new QWellCommissionPassportEntity("wellCommissionPassportEntity");

    public final QWellCommissionOpeningPolicyEntity _super;

    public final NumberPath<Integer> depositDeduction = createNumber("depositDeduction", Integer.class);

    public final NumberPath<Integer> feeCash = createNumber("feeCash", Integer.class);

    public final NumberPath<Integer> feeDeposit = createNumber("feeDeposit", Integer.class);

    public final NumberPath<Integer> feePpsCard = createNumber("feePpsCard", Integer.class);

    public final NumberPath<Integer> initialCharge = createNumber("initialCharge", Integer.class);

    public final StringPath operatorIdx = createString("operatorIdx");

    //inherited
    public final StringPath passportType;

    public final NumberPath<Integer> policyQuantity = createNumber("policyQuantity", Integer.class);

    // inherited
    public final com.wellnetworks.wellcore.java.domain.product.QWellProductEntity product;

    public final StringPath productIdx = createString("productIdx");

    //inherited
    public final StringPath registerDate;

    //inherited
    public final StringPath versionId;

    public QWellCommissionPassportEntity(String variable) {
        this(WellCommissionPassportEntity.class, forVariable(variable), INITS);
    }

    public QWellCommissionPassportEntity(Path<? extends WellCommissionPassportEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellCommissionPassportEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellCommissionPassportEntity(PathMetadata metadata, PathInits inits) {
        this(WellCommissionPassportEntity.class, metadata, inits);
    }

    public QWellCommissionPassportEntity(Class<? extends WellCommissionPassportEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QWellCommissionOpeningPolicyEntity(type, metadata, inits);
        this.passportType = _super.passportType;
        this.product = _super.product;
        this.registerDate = _super.registerDate;
        this.versionId = _super.versionId;
    }

}

