package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.ContactRejectType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class ContactRejectTypeToIndexConverter : AttributeConverter<ContactRejectType, Byte> {
    override fun convertToDatabaseColumn(attribute: ContactRejectType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): ContactRejectType {
        if (dbData == null) return ContactRejectType.CONTACT_REJECT_TYPE_UNKNOWN;
        return ContactRejectType from dbData.toInt() ?: ContactRejectType.CONTACT_REJECT_TYPE_UNKNOWN;
    }
}