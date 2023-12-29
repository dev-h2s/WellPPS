package com.wellnetworks.wellcore.java.domain.apikeyIn;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellApikeyInEntity is a Querydsl query type for WellApikeyInEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellApikeyInEntity extends EntityPathBase<WellApikeyInEntity> {

    private static final long serialVersionUID = -287581906L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellApikeyInEntity wellApikeyInEntity = new QWellApikeyInEntity("wellApikeyInEntity");

    public final StringPath apiKeyIn = createString("apiKeyIn");

    public final BooleanPath apiKeyInEndFlag = createBoolean("apiKeyInEndFlag");

    public final StringPath apiKeyInIdx = createString("apiKeyInIdx");

    public final DatePath<java.time.LocalDate> apiKeyInRegisterDate = createDate("apiKeyInRegisterDate", java.time.LocalDate.class);

    public final ListPath<String, StringPath> apiServerIp = this.<String, StringPath>createList("apiServerIp", String.class, StringPath.class, PathInits.DIRECT2);

    public final BooleanPath asia = createBoolean("asia");

    public final BooleanPath dream = createBoolean("dream");

    public final BooleanPath home = createBoolean("home");

    public final StringPath issuer = createString("issuer");

    public final BooleanPath iz = createBoolean("iz");

    public final StringPath memo = createString("memo");

    public final StringPath partnerIdx = createString("partnerIdx");

    public final com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity partners;

    public final BooleanPath PDS = createBoolean("PDS");

    public final ListPath<String, StringPath> serverUrl = this.<String, StringPath>createList("serverUrl", String.class, StringPath.class, PathInits.DIRECT2);

    public final BooleanPath valueCom = createBoolean("valueCom");

    public QWellApikeyInEntity(String variable) {
        this(WellApikeyInEntity.class, forVariable(variable), INITS);
    }

    public QWellApikeyInEntity(Path<? extends WellApikeyInEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellApikeyInEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellApikeyInEntity(PathMetadata metadata, PathInits inits) {
        this(WellApikeyInEntity.class, metadata, inits);
    }

    public QWellApikeyInEntity(Class<? extends WellApikeyInEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partners = inits.isInitialized("partners") ? new com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity(forProperty("partners"), inits.get("partners")) : null;
    }

}

