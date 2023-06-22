package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellProductEntity
import com.wellnetworks.wellcore.domain.dto.WellProductDTOUpdate
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.repository.WellProductRepository
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.utils.WellServiceUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.*

@Component
class WellProductService {

    @Autowired
    private lateinit var wellProductRepository: WellProductRepository

    @Transactional(rollbackFor = [Exception::class])
    fun createProduct(product: WellProductDTOs): Boolean {

            var productIdx = UUID.randomUUID().toString().uppercase()

            var cProduct = WellProductEntity(
                productIdx,product.OperatorName,
                product.OperatorCode.toString(),product.ProductName,product.ProductCodeIn,
                product.ProductCodeEx,product.Product_Type,product.ProductPrice,product.ProductInfoData,product.ProductInfoVoice,
                product.ProductInfoSms,product.ProductInfoEtc,product.Telecom_Type,product.Monthly,product.VisibleFlag,product.RunFlag,
                product.ProductMemo,product.Sort1,0,0,product.Register_Datetime,product.Modify_Datetime
            )
        try {
            wellProductRepository.save(cProduct)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    fun getProductByIdx(idx: String): Optional<WellProductDTOs> {
        val product = wellProductRepository.findByIdx(idx.uppercase())
        return product.map { it.toDto() }
    }

    fun searchProduct(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellProductDTOs> {
        if (searchKeyword.isNullOrEmpty()) {
            return wellProductRepository.findAll(pageable).map { it.toDto() }
        }

        return wellProductRepository.findAll(
            WellServiceUtil.Specification<WellProductEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun updateProduct(product: WellProductDTOUpdate) : Boolean {
        try{
            val currentEntity = wellProductRepository.findByIdx(product.Idx.toString().uppercase()).orElse(null)?: return false

            currentEntity.updateDto(product)
            currentEntity.modifyDatetime = ZonedDateTime.now()
            wellProductRepository.save(currentEntity)

        } catch (e: Exception){
            return false
        }

        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    fun deleteProductById(idx: String) {
        val product = wellProductRepository.deleteByIdx(idx)

        if (product.get() == 1) {
            return
        }

        throw Exception("delete count not match.")
    }

    data class productCountAll(val totalTelecom: Long, val runTelecom: Long, val totalProduct: Long, val runProduct: Long)

    fun productCount(): productCountAll {
        val totalTelecom = wellProductRepository.countByOperatorNameIsNotNull()
        val runTelecom = wellProductRepository.countByRunFlagIsTrueAndOperatorNameIsNotNull()
        val totalProduct = wellProductRepository.countByProductNameIsNotNull()
        val runProduct = wellProductRepository.countByRunFlagIsTrueAndProductNameIsNotNull()

        return productCountAll(totalTelecom, runTelecom, totalProduct, runProduct)
    }
}