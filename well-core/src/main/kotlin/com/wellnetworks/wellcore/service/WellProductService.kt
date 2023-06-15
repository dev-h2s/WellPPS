package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellProductEntity
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.domain.enums.PermissionKey
import com.wellnetworks.wellcore.repository.WellProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class WellProductService {

    @Autowired
    private lateinit var wellProductRepository: WellProductRepository

    @Transactional(rollbackFor = [Exception::class])
    fun createProduct(product: WellProductDTOs): Boolean {

            var productIdx = UUID.randomUUID().toString().uppercase()

            var cProduct = WellProductEntity(
                productIdx,product.OperatorName,product.OperatorCode,product.ProductName,product.ProductCodeIn,
                product.ProductCodeEx,product.Product_Type,product.ProductPrice,product.ProductInfoData,product.ProductInfoVoice,
                product.ProductInfoSms,product.ProductInfoEtc,product.Telecom_Type,product.Monthly,product.VisibleFlag,product.RunFlag,
                product.ProductMemo,0,0,0,product.Register_Datetime,product.Modify_Datetime
            )
        try {
            wellProductRepository.save(cProduct)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }
}