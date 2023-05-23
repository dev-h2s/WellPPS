package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.ProductType
import jakarta.persistence.AttributeConverter


class ProductTypeToIndexConverter : AttributeConverter<ProductType, Byte> {
    override fun convertToDatabaseColumn(attribute: ProductType?): Byte {
        if (attribute == null) return 0

        return attribute.index().toByte()
    }

    override fun convertToEntityAttribute(dbData: Byte?): ProductType {
        if (dbData == null) return ProductType.PRODUCT_TYPE_UNKNOWN;
        return ProductType from dbData.toInt() ?: ProductType.PRODUCT_TYPE_UNKNOWN;
    }
}