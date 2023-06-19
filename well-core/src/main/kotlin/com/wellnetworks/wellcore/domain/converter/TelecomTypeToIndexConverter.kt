package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.TelecomType
import jakarta.persistence.AttributeConverter

class TelecomTypeToIndexConverter : AttributeConverter<TelecomType, Byte>  {

    override fun convertToDatabaseColumn(attribute: TelecomType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): TelecomType {
        if (dbData == null) return TelecomType.TELECOM_TYPE_UNKNOWN;
        return TelecomType from dbData.toInt() ?: TelecomType.TELECOM_TYPE_UNKNOWN;
    }

}