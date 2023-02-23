package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.CompanyStateType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class CompanyStateTypeToIndexConverter : AttributeConverter<CompanyStateType, Byte> {
    override fun convertToDatabaseColumn(attribute: CompanyStateType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): CompanyStateType {
        if (dbData == null) return CompanyStateType.COMPANY_STATE_TYPE_UNKNOWN;
        return CompanyStateType from dbData.toInt() ?: CompanyStateType.COMPANY_STATE_TYPE_UNKNOWN;
    }
}