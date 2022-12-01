package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.JobPositionType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class JobPositionTypeToIndexConverter : AttributeConverter<JobPositionType, Byte> {
    override fun convertToDatabaseColumn(attribute: JobPositionType?): Byte {
        if (attribute == null) return -1

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): JobPositionType {
        if (dbData == null) return JobPositionType.JOB_POSITION_TYPE_UNKNOWN;
        return JobPositionType from dbData.toInt() ?: JobPositionType.JOB_POSITION_TYPE_UNKNOWN;
    }
}