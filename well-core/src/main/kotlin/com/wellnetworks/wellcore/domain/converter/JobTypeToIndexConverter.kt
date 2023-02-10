package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.JobType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class JobTypeToIndexConverter : AttributeConverter<JobType, Byte> {
    override fun convertToDatabaseColumn(attribute: JobType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): JobType {
        if (dbData == null) return JobType.JOB_TYPE_UNKNOWN;
        return JobType from dbData.toInt() ?: JobType.JOB_TYPE_UNKNOWN;
    }
}