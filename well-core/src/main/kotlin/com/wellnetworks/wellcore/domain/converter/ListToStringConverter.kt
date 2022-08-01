package com.wellnetworks.wellcore.domain.converter

import org.apache.tomcat.util.buf.StringUtils
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class ListToStringConverter(): AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        if (attribute.isNullOrEmpty())
            return ""

        return StringUtils.join(attribute, ',')
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        if (dbData.isNullOrEmpty()) {
            return ArrayList<String>()
        }

        return dbData.split(",")
    }
}