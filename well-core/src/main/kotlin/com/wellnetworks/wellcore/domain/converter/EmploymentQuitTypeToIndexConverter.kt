package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.EmploymentQuitType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class EmploymentQuitTypeToIndexConverter : AttributeConverter<EmploymentQuitType, Byte> {
    override fun convertToDatabaseColumn(attribute: EmploymentQuitType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): EmploymentQuitType {
        if (dbData == null) return EmploymentQuitType.EMPLOYMENT_QUIT_TYPE_UNKNOWN;
        return EmploymentQuitType from dbData.toInt() ?: EmploymentQuitType.EMPLOYMENT_QUIT_TYPE_UNKNOWN;
    }
}