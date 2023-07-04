package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.OpeningType
import jakarta.persistence.AttributeConverter


class OpeningTypeToIndexConverter : AttributeConverter<OpeningType, Byte> {
    override fun convertToDatabaseColumn(attribute: OpeningType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): OpeningType {
        if (dbData == null) return OpeningType.OPENING_TYPE_UNKNOWN
        return OpeningType from dbData.toInt() ?: OpeningType.OPENING_TYPE_UNKNOWN
    }
}