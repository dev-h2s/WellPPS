package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.CompanyStateType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class CompanyStateTypeToIndexConverter : AttributeConverter<CompanyStateType, Byte> {
    override fun convertToDatabaseColumn(attribute: CompanyStateType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): CompanyStateType {
        if (dbData == null) return CompanyStateType.COMPANY_STATE_TYPE_UNKNOWN;
        return CompanyStateType from dbData.toInt() ?: CompanyStateType.COMPANY_STATE_TYPE_UNKNOWN;
    }
}