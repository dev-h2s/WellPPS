package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.CompanyType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class CompanyTypeToIndexConverter : AttributeConverter<CompanyType, Byte> {
    override fun convertToDatabaseColumn(attribute: CompanyType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): CompanyType {
        if (dbData == null) return CompanyType.COMPANY_TYPE_UNKNOWN;
        return CompanyType from dbData.toInt() ?: CompanyType.COMPANY_TYPE_UNKNOWN;
    }
}