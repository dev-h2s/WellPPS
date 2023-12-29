package com.wellnetworks.wellcore.java.domain.employee;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellEmployeeEntity is a Querydsl query type for WellEmployeeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellEmployeeEntity extends EntityPathBase<WellEmployeeEntity> {

    private static final long serialVersionUID = 469214126L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellEmployeeEntity wellEmployeeEntity = new QWellEmployeeEntity("wellEmployeeEntity");

    public final StringPath bankAccount = createString("bankAccount");

    public final StringPath bankHolder = createString("bankHolder");

    public final StringPath bankName = createString("bankName");

    public final StringPath belong = createString("belong");

    public final BooleanPath dbAccessPower = createBoolean("dbAccessPower");

    public final StringPath email = createString("email");

    public final NumberPath<Long> employeeId = createNumber("employeeId", Long.class);

    public final StringPath employeeIdx = createString("employeeIdx");

    public final StringPath employeeName = createString("employeeName");

    public final QWellEmployeeUserEntity employeeUser;

    public final StringPath employmentQuitType = createString("employmentQuitType");

    public final StringPath employmentState = createString("employmentState");

    public final DatePath<java.time.LocalDate> entryDate = createDate("entryDate", java.time.LocalDate.class);

    public final BooleanPath externalAccessCert = createBoolean("externalAccessCert");

    public final StringPath homeAddress1 = createString("homeAddress1");

    public final StringPath homeAddress2 = createString("homeAddress2");

    public final StringPath jobType = createString("jobType");

    public final StringPath memo = createString("memo");

    public final StringPath position = createString("position");

    public final StringPath registrationNumber = createString("registrationNumber");

    public final NumberPath<Float> remainingLeaveDays = createNumber("remainingLeaveDays", Float.class);

    public final StringPath residentRegistrationNumber = createString("residentRegistrationNumber");

    public final DatePath<java.time.LocalDate> retireDate = createDate("retireDate", java.time.LocalDate.class);

    public final StringPath telPrivate = createString("telPrivate");

    public final StringPath telWork = createString("telWork");

    public QWellEmployeeEntity(String variable) {
        this(WellEmployeeEntity.class, forVariable(variable), INITS);
    }

    public QWellEmployeeEntity(Path<? extends WellEmployeeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellEmployeeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellEmployeeEntity(PathMetadata metadata, PathInits inits) {
        this(WellEmployeeEntity.class, metadata, inits);
    }

    public QWellEmployeeEntity(Class<? extends WellEmployeeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employeeUser = inits.isInitialized("employeeUser") ? new QWellEmployeeUserEntity(forProperty("employeeUser"), inits.get("employeeUser")) : null;
    }

}

