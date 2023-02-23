package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.ContactRejectType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ContactRejectTypeToIndexConverter : AttributeConverter<ContactRejectType, Byte> {
    override fun convertToDatabaseColumn(attribute: ContactRejectType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): ContactRejectType {
        if (dbData == null) return ContactRejectType.CONTACT_REJECT_TYPE_UNKNOWN;
        return ContactRejectType from dbData.toInt() ?: ContactRejectType.CONTACT_REJECT_TYPE_UNKNOWN;
    }
}