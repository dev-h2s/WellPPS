package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.ContactType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class ContactTypeToIndexConverter : AttributeConverter<ContactType, Byte> {
    override fun convertToDatabaseColumn(attribute: ContactType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): ContactType {
        if (dbData == null) return ContactType.CONTACT_TYPE_UNKNOWN;
        return ContactType from dbData.toInt() ?: ContactType.CONTACT_TYPE_UNKNOWN;
    }
}