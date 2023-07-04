package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.WriteType
import jakarta.persistence.AttributeConverter


class WriteTypeToIndexConverter : AttributeConverter<WriteType, Byte> {
    override fun convertToDatabaseColumn(attribute: WriteType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): WriteType {
        if (dbData == null) return WriteType.WRITE_TYPE_UNKNOWN
        return WriteType from dbData.toInt() ?: WriteType.WRITE_TYPE_UNKNOWN
    }
}