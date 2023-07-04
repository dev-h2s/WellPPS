package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.PaymentType
import jakarta.persistence.AttributeConverter


class PaymentTypeToIndexConverter : AttributeConverter<PaymentType, Byte> {
    override fun convertToDatabaseColumn(attribute: PaymentType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): PaymentType {
        if (dbData == null) return PaymentType.PAYMENT_TYPE_UNKNOWN;
        return PaymentType from dbData.toInt() ?: PaymentType.PAYMENT_TYPE_UNKNOWN;
    }
}