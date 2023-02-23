package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.AgreeType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class AgreeTypeToIndexConverter : AttributeConverter<AgreeType, Byte> {
    override fun convertToDatabaseColumn(attribute: AgreeType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): AgreeType {
        if (dbData == null) return AgreeType.AGREE_TYPE_UNKNOWN;
        return AgreeType from dbData.toInt() ?: AgreeType.AGREE_TYPE_UNKNOWN;
    }
}