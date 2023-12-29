package com.wellnetworks.wellcore.java.dto.FIle;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.wellnetworks.wellcore.java.dto.FIle.QWellPartnerFileCreateDTO is a Querydsl Projection type for WellPartnerFileCreateDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QWellPartnerFileCreateDTO extends ConstructorExpression<WellPartnerFileCreateDTO> {

    private static final long serialVersionUID = -249931081L;

    public QWellPartnerFileCreateDTO(com.querydsl.core.types.Expression<Long> partnerFileId, com.querydsl.core.types.Expression<Long> fileId, com.querydsl.core.types.Expression<String> originFileName, com.querydsl.core.types.Expression<Long> size, com.querydsl.core.types.Expression<String> extension) {
        super(WellPartnerFileCreateDTO.class, new Class<?>[]{long.class, long.class, String.class, long.class, String.class}, partnerFileId, fileId, originFileName, size, extension);
    }

}

