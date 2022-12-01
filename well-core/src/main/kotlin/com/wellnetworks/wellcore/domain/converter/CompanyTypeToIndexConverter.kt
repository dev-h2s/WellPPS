package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.CompanyType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class CompanyTypeToIndexConverter : AttributeConverter<CompanyType, Byte> {
    override fun convertToDatabaseColumn(attribute: CompanyType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): CompanyType {
        if (dbData == null) return CompanyType.COMPANY_TYPE_UNKNOWN;
        return CompanyType from dbData.toInt() ?: CompanyType.COMPANY_TYPE_UNKNOWN;
    }
}