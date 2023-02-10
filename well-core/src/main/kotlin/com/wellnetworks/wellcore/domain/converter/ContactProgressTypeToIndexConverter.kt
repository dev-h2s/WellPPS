package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.ContactProgressType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class ContactProgressTypeToIndexConverter : AttributeConverter<ContactProgressType, Byte> {
    override fun convertToDatabaseColumn(attribute: ContactProgressType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): ContactProgressType {
        if (dbData == null) return ContactProgressType.CONTACT_PROGRESS_TYPE_UNKNOWN;
        return ContactProgressType from dbData.toInt() ?: ContactProgressType.CONTACT_PROGRESS_TYPE_UNKNOWN;
    }
}