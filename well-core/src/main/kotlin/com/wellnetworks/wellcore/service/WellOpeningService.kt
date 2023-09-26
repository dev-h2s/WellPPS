package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellOpeningEntity
import com.wellnetworks.wellcore.domain.WellProductEntity
import com.wellnetworks.wellcore.domain.dto.WellOpeningDTO
import com.wellnetworks.wellcore.domain.dto.WellOpeningDTOUpdate
import com.wellnetworks.wellcore.domain.dto.WellProductDTOUpdate
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.repository.WellOpeningRepository
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
class WellOpeningService {

    @Autowired
    private lateinit var wellOpeningRepository: WellOpeningRepository

    // 개통 정보 생성 메서드
    @Transactional(rollbackFor = [Exception::class])
    fun createOpening(opening: WellOpeningDTO): Boolean {
        // UUID를 사용하여 개통 정보의 고유 식별자 생성
        var openingIdx = UUID.randomUUID().toString().uppercase()

        // WellOpeningDTO에서 WellOpeningEntity로 데이터 복사
        var cOpening = WellOpeningEntity(
            openingIdx, opening.UserIdx.toString(),opening.UserSubIdx.toString(),
            opening.OpeningType,opening.OperatorCode,opening.ProductCodeIn,opening.PhoneNum,
            opening.CustomerName,opening.Passport,opening.Country,opening.ModelNo,opening.PaymentType,
            opening.LocalType,opening.Incharge,opening.UserName,opening.UserId,opening.CheckReview,
            opening.Inspector,opening.AutoCharge,opening.WriteType,opening.Commission1,
            opening.Commission2,opening.Commission3

        )
        try {
            //repository에 입력받은 정보를 저장
            wellOpeningRepository.save(cOpening)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    // 개통 정보 조회 메서드
    fun getOpeningByIdx(idx: String): Optional<WellOpeningDTO> {
        // 개통 정보 식별자로 조회
        val opening = wellOpeningRepository.findByIdx(idx.uppercase())
        return opening.map { it.toDto() }
    }
    // 개통 정보 검색 메서드
    fun searchOpening(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellOpeningDTO> {
        if (searchKeyword.isNullOrEmpty()) {
            // 검색어가 없는 경우, 모든 개통 정보 페이지 반환
            return wellOpeningRepository.findAll(pageable).map { it.toDto() }
        }
        // 검색 조건을 사용하여 개통 정보 검색 후 페이지 반환
        return wellOpeningRepository.findAll(
            WellServiceUtil.Specification<WellOpeningEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }
    // 개통 정보 업데이트 메서드
    @Transactional(rollbackFor = [Exception::class])
    fun updateOpening(opening: WellOpeningDTOUpdate) : Boolean {
        try{
            // 개통 정보 식별자로 현재 엔터티 조회
            val currentEntity = wellOpeningRepository.findByIdx(opening.Idx.toString().uppercase()).orElse(null)?: return false

            // WellOpeningDTOUpdate로 엔터티 업데이트
            currentEntity.updateDto(opening)
            currentEntity.modifyDatetime = ZonedDateTime.now().plusHours(9)

            // 엔터티 저장
            wellOpeningRepository.save(currentEntity)

        } catch (e: Exception){
            return false
        }

        return true
    }

    // 개통 정보 삭제 메서드
    @Transactional(rollbackFor = [Exception::class])
    fun deleteOpeningById(idx: String) {
        // 개통 정보 식별자로 개통 정보 삭제
        val product = wellOpeningRepository.deleteByIdx(idx)

        if (product.get() == 1) {
            return
        }

        throw Exception("delete count not match.")
    }

}