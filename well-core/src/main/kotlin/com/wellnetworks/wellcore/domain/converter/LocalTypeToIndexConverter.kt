package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.LocalType
import jakarta.persistence.AttributeConverter

class LocalTypeToIndexConverter : AttributeConverter<LocalType, Byte> {
    override fun convertToDatabaseColumn(attribute: LocalType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): LocalType {
        if (dbData == null) return LocalType.LOCAL_TYPE_UNKNOWN
        return LocalType from dbData.toInt() ?: LocalType.LOCAL_TYPE_UNKNOWN
    }
}