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
    // WellProductRepository(dao)를 주입받는다.
    @Autowired
    private lateinit var wellProductRepository: WellProductRepository

    // 통신사/요금제 생성하는 메서드
    @Transactional(rollbackFor = [Exception::class])
    fun createProduct(product: WellProductDTOs): Boolean {

            // 랜덤 uuid 할당
            var productIdx = UUID.randomUUID().toString().uppercase()
            // 제품 정보를 WellProductEntity 객체로 변환
            // 실제 DB 저장하기에 entity 사용
            var cProduct = WellProductEntity(
                productIdx,product.OperatorName,
                product.OperatorCode.toString(),product.ProductName,product.ProductCodeIn,
                product.ProductCodeEx,product.Product_Type,product.ProductPrice,product.ProductInfoData,product.ProductInfoVoice,
                product.ProductInfoSms,product.ProductInfoEtc,product.Telecom_Type,product.Monthly,product.VisibleFlag,product.RunFlag,
                product.ProductMemo,product.Sort1,0,0
            )
        try {
            //변수에 담긴 정보들 저장
            wellProductRepository.save(cProduct)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    // ID에 해당하는 제품을 조회하는 메서드
    // 가공된 DB에 대한것은 DTO 사용
    fun getProductByIdx(idx: String): Optional<WellProductDTOs> {
        val product = wellProductRepository.findByIdx(idx.uppercase())
        return product.map { it.toDto() }
    }
    // 제품을 검색하고 페이지네이션을 적용하는 메서드
    fun searchProduct(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellProductDTOs> {
        if (searchKeyword.isNullOrEmpty()) {
            // 검색 조건이 없을 경우 모든 제품을 조회하고 페이지네이션을 적용합니다.
            return wellProductRepository.findAll(pageable).map { it.toDto() }
        }

        // 검색 조건이 있을 경우 검색 조건을 적용하여 제품을 조회하고 페이지네이션을 적용합니다.
        return wellProductRepository.findAll( // findall : 모든데이터를 가져온다
            //Specification : 검색 조건을 지정하는 방법
            //searchKeyword : 검색 조건을 나타내는 리스트
            WellServiceUtil.Specification<WellProductEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }

    // 제품 정보를 업데이트하는 메서드
    //  @Transactional:범위를 지정 범위 내에서 실행된 모든 데이터베이스 작업은 하나의 트랜잭션으로 묶이며 예외가 발생하더라도 롤백 일관성 유지
    @Transactional(rollbackFor = [Exception::class]) // 모든 예외가 발생시 롤백
    fun updateProduct(product: WellProductDTOUpdate) : Boolean {
        try{
            // 업데이트할 제품을 ID에 해당하는 것을 찾아서 업데이트
            val currentEntity = wellProductRepository.findByIdx(product.Idx.toString().uppercase()).orElse(null)?: return false

            // 제품 정보를 업데이트
            currentEntity.updateDto(product)
            currentEntity.modifyDatetime = ZonedDateTime.now().plusHours(9)
            wellProductRepository.save(currentEntity)

        } catch (e: Exception){
            return false
        }

        return true
    }

    // 제품을 삭제하는 메서드
    @Transactional(rollbackFor = [Exception::class])
    fun deleteProductById(idx: String) {
        val product = wellProductRepository.deleteByIdx(idx)

        if (product.get() == 1) {
            return // 삭제된 제품의 수 반환
        }

        throw Exception("delete count not match.")
    }
    // 제품 카운트 정보를 조회하는 메서드(계산)
    data class productCountAll(val totalTelecom: Long, val runTelecom: Long, val totalProduct: Long, val runProduct: Long)

    fun productCount(): productCountAll {
        // 각각의 제품 카운트 정보를 조회합니다.
        val totalTelecom = wellProductRepository.countBySort1Equals(1)
        val runTelecom = wellProductRepository.countByRunFlagIsTrueAndSort1Equals(1)
        val totalProduct = wellProductRepository.countBySort1Equals(2)
        val runProduct = wellProductRepository.countByRunFlagIsTrueAndSort1Equals(2)

        return productCountAll(totalTelecom, runTelecom, totalProduct, runProduct)
    }
}