package com.wellnetworks.wellcore.java.dto.FIle;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.wellnetworks.wellcore.java.dto.FIle.QWellEmployeeFileCreateDTO is a Querydsl Projection type for WellEmployeeFileCreateDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QWellEmployeeFileCreateDTO extends ConstructorExpression<WellEmployeeFileCreateDTO> {

    private static final long serialVersionUID = 268950177L;

    public QWellEmployeeFileCreateDTO(com.querydsl.core.types.Expression<Long> employeeFileId, com.querydsl.core.types.Expression<Long> fileId, com.querydsl.core.types.Expression<String> originFileName, com.querydsl.core.types.Expression<Long> size, com.querydsl.core.types.Expression<String> extension) {
        super(WellEmployeeFileCreateDTO.class, new Class<?>[]{long.class, long.class, String.class, long.class, String.class}, employeeFileId, fileId, originFileName, size, extension);
    }

}

