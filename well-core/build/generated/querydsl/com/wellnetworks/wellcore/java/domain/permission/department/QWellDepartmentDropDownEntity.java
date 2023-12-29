package com.wellnetworks.wellcore.java.domain.permission.department;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWellDepartmentDropDownEntity is a Querydsl query type for WellDepartmentDropDownEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWellDepartmentDropDownEntity extends EntityPathBase<WellDepartmentDropDownEntity> {

    private static final long serialVersionUID = -1838487070L;

    public static final QWellDepartmentDropDownEntity wellDepartmentDropDownEntity = new QWellDepartmentDropDownEntity("wellDepartmentDropDownEntity");

    public final NumberPath<Long> depDropId = createNumber("depDropId", Long.class);

    public final StringPath menuName = createString("menuName");

    public QWellDepartmentDropDownEntity(String variable) {
        super(WellDepartmentDropDownEntity.class, forVariable(variable));
    }

    public QWellDepartmentDropDownEntity(Path<? extends WellDepartmentDropDownEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWellDepartmentDropDownEntity(PathMetadata metadata) {
        super(WellDepartmentDropDownEntity.class, metadata);
    }

}

