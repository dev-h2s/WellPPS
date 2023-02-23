package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.ContactType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ContactTypeToIndexConverter : AttributeConverter<ContactType, Byte> {
    override fun convertToDatabaseColumn(attribute: ContactType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): ContactType {
        if (dbData == null) return ContactType.CONTACT_TYPE_UNKNOWN;
        return ContactType from dbData.toInt() ?: ContactType.CONTACT_TYPE_UNKNOWN;
    }
}