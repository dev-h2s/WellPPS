package com.wellnetworks.wellcore.domain.converter

import com.wellnetworks.wellcore.domain.enums.ProductType
import jakarta.persistence.AttributeConverter


class ProductTypeToIndexConverter : AttributeConverter<ProductType, Byte> {
    // 열거형을 데이터베이스 컬럼에 저장할 때 호출되는 메서드
    override fun convertToDatabaseColumn(attribute: ProductType?): Byte {
        if (attribute == null) return 0
        // 열거형의 index를 바이트로 변환하여 반환
        return attribute.index().toByte()
    }

    // 데이터베이스 컬럼에서 열거형을 읽어올 때 호출되는 메서드
    override fun convertToEntityAttribute(dbData: Byte?): ProductType {
        if (dbData == null) return ProductType.PRODUCT_TYPE_UNKNOWN;
        return ProductType from dbData.toInt() ?: ProductType.PRODUCT_TYPE_UNKNOWN;
    }
}