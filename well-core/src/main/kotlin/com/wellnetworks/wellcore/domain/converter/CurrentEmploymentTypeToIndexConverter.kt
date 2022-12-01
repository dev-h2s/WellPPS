package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.CurrentEmploymentType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class CurrentEmploymentTypeToIndexConverter : AttributeConverter<CurrentEmploymentType, Byte> {
    override fun convertToDatabaseColumn(attribute: CurrentEmploymentType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): CurrentEmploymentType {
        if (dbData == null) return CurrentEmploymentType.CURRENT_EMPLOYMENT_TYPE_UNKNOWN;
        return CurrentEmploymentType from dbData.toInt() ?: CurrentEmploymentType.CURRENT_EMPLOYMENT_TYPE_UNKNOWN;
    }
}