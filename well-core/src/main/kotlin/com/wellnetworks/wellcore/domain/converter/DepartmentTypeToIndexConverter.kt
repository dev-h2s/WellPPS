package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.DepartmentType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class DepartmentTypeToIndexConverter : AttributeConverter<DepartmentType, Byte> {
    override fun convertToDatabaseColumn(attribute: DepartmentType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): DepartmentType {
        if (dbData == null) return DepartmentType.DEPARTMENT_TYPE_UNKNOWN;
        return DepartmentType from dbData.toInt() ?: DepartmentType.DEPARTMENT_TYPE_UNKNOWN;
    }
}