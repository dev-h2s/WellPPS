package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.RateType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class RateTypeToIndexConverter : AttributeConverter<RateType, Byte> {
    override fun convertToDatabaseColumn(attribute: RateType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): RateType {
        if (dbData == null) return RateType.RATE_TYPE_UNKNOWN;
        return RateType from dbData.toInt() ?: RateType.RATE_TYPE_UNKNOWN;
    }
}