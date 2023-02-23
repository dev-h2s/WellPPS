package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.EmploymentQuitType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class EmploymentQuitTypeToIndexConverter : AttributeConverter<EmploymentQuitType, Byte> {
    override fun convertToDatabaseColumn(attribute: EmploymentQuitType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): EmploymentQuitType {
        if (dbData == null) return EmploymentQuitType.EMPLOYMENT_QUIT_TYPE_UNKNOWN;
        return EmploymentQuitType from dbData.toInt() ?: EmploymentQuitType.EMPLOYMENT_QUIT_TYPE_UNKNOWN;
    }
}