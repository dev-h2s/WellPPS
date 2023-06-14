package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellProductEntity
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.domain.enums.PermissionKey
import com.wellnetworks.wellcore.repository.WellProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WellProductService {

    @Autowired
    private lateinit var wellProductRepository: WellProductRepository

    @Autowired
    private lateinit var wellProductService: WellProductService

    @Transactional(rollbackFor = [Exception::class])
    fun createProduct(product: WellProductDTOs): Boolean {
        try {
            var productIdx: String = (wellProductService.createProduct(product) ?: throw Exception("데이터 생성에 실패하였습니다.")).toString()

            var cProduct = WellProductEntity(
                productIdx,product.OperatorName,product.OperatorCode,product.ProductName,product.ProductCodeIn,
                product.ProductCodeEx,product.Product_Type,product.ProductPrice,product.ProductInfoData,product.ProductInfoVoice,
                product.ProductInfoSms,product.ProductInfoEtc,product.Telecom_Type,product.Monthly,product.VisibleFlag,product.RunFlag,
                product.ProductMemo,0,0,0,product.Register_Datetime,product.Modify_Datetime
            )

            val permissions: List<String> = listOf (
                PermissionKey.MEMBER_SCREENING,
            )

            //wellProductRepository.save(cProduct)
        } catch (e: Exception) {
            return false
        }
        return true
    }
}