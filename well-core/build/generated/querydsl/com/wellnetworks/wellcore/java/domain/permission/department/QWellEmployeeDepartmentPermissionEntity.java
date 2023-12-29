package com.wellnetworks.wellcore.java.domain.permission.department;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWellEmployeeDepartmentPermissionEntity is a Querydsl query type for WellEmployeeDepartmentPermissionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellEmployeeDepartmentPermissionEntity extends EntityPathBase<WellEmployeeDepartmentPermissionEntity> {

    private static final long serialVersionUID = -1452077362L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWellEmployeeDepartmentPermissionEntity wellEmployeeDepartmentPermissionEntity = new QWellEmployeeDepartmentPermissionEntity("wellEmployeeDepartmentPermissionEntity");

    public final QWellDepartmentDropDownEntity departmentDropDown;

    public final NumberPath<Long> emDepId = createNumber("emDepId", Long.class);

    public final com.wellnetworks.wellcore.java.domain.employee.QWellEmployeeEntity employee;

    public QWellEmployeeDepartmentPermissionEntity(String variable) {
        this(WellEmployeeDepartmentPermissionEntity.class, forVariable(variable), INITS);
    }

    public QWellEmployeeDepartmentPermissionEntity(Path<? extends WellEmployeeDepartmentPermissionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWellEmployeeDepartmentPermissionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWellEmployeeDepartmentPermissionEntity(PathMetadata metadata, PathInits inits) {
        this(WellEmployeeDepartmentPermissionEntity.class, metadata, inits);
    }

    public QWellEmployeeDepartmentPermissionEntity(Class<? extends WellEmployeeDepartmentPermissionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.departmentDropDown = inits.isInitialized("departmentDropDown") ? new QWellDepartmentDropDownEntity(forProperty("departmentDropDown")) : null;
        this.employee = inits.isInitialized("employee") ? new com.wellnetworks.wellcore.java.domain.employee.QWellEmployeeEntity(forProperty("employee"), inits.get("employee")) : null;
    }

}

