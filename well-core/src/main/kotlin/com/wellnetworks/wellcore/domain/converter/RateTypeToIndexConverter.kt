package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.RateType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class RateTypeToIndexConverter : AttributeConverter<RateType, Byte> {
    override fun convertToDatabaseColumn(attribute: RateType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): RateType {
        if (dbData == null) return RateType.RATE_TYPE_UNKNOWN;
        return RateType from dbData.toInt() ?: RateType.RATE_TYPE_UNKNOWN;
    }
}