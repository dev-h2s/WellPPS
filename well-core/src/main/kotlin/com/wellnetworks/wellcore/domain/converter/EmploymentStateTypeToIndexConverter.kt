package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.EmploymentStateType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class EmploymentStateTypeToIndexConverter : AttributeConverter<EmploymentStateType, Byte> {
    override fun convertToDatabaseColumn(attribute: EmploymentStateType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): EmploymentStateType {
        if (dbData == null) return EmploymentStateType.EMPLOYMENT_STATE_TYPE_UNKNOWN;
        return EmploymentStateType from dbData.toInt() ?: EmploymentStateType.EMPLOYMENT_STATE_TYPE_UNKNOWN;
    }
}