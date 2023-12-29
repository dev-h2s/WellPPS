package com.wellnetworks.wellcore.java.domain.operator;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellOperatorEntity is a Querydsl query type for WellOperatorEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellOperatorEntity extends EntityPathBase<WellOperatorEntity> {

    private static final long serialVersionUID = 1855075182L;

    public static final QWellOperatorEntity wellOperatorEntity = new QWellOperatorEntity("wellOperatorEntity");

    public final BooleanPath isExternalApiFlag = createBoolean("isExternalApiFlag");

    public final BooleanPath isOpeningSearchFlag = createBoolean("isOpeningSearchFlag");

    public final BooleanPath isPdsFlag = createBoolean("isPdsFlag");

    public final BooleanPath isRunFlag = createBoolean("isRunFlag");

    public final BooleanPath isVisibleFlag = createBoolean("isVisibleFlag");

    public final StringPath operatorCode = createString("operatorCode");

    public final StringPath operatorIdx = createString("operatorIdx");

    public final StringPath operatorName = createString("operatorName");

    public final ListPath<com.wellnetworks.wellcore.java.domain.product.WellProductEntity, com.wellnetworks.wellcore.java.domain.product.QWellProductEntity> products = this.<com.wellnetworks.wellcore.java.domain.product.WellProductEntity, com.wellnetworks.wellcore.java.domain.product.QWellProductEntity>createList("products", com.wellnetworks.wellcore.java.domain.product.WellProductEntity.class, com.wellnetworks.wellcore.java.domain.product.QWellProductEntity.class, PathInits.DIRECT2);

    public QWellOperatorEntity(String variable) {
        super(WellOperatorEntity.class, forVariable(variable));
    }

    public QWellOperatorEntity(Path<? extends WellOperatorEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellOperatorEntity(PathMetadata metadata) {
        super(WellOperatorEntity.class, metadata);
    }

}

