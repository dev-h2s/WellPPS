package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellOpeningEntity
import com.wellnetworks.wellcore.domain.WellProductEntity
import com.wellnetworks.wellcore.domain.dto.WellOpeningDTO
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.repository.WellOpeningRepository
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.utils.WellServiceUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class WellOpeningService {

    @Autowired
    private lateinit var wellOpeningRepository: WellOpeningRepository

    @Transactional(rollbackFor = [Exception::class])
    fun createOpening(opening: WellOpeningDTO): Boolean {

        var openingIdx = UUID.randomUUID().toString().uppercase()

        var cOpening = WellOpeningEntity(
            openingIdx, opening.UserIdx.toString(),opening.UserSubIdx.toString(),
            opening.OpeningType,opening.OperatorCode,opening.ProductCodeIn,opening.PhoneNum,
            opening.CustomerName,opening.Passport,opening.Country,opening.ModelNo,opening.PaymentType,
            opening.LocalType,opening.Incharge,opening.UserName,opening.UserId,opening.CheckReview,
            opening.Inspector,opening.AutoCharge,opening.WriteType,opening.Commission1,
            opening.Commission2,opening.Commission3

        )
        try {
            wellOpeningRepository.save(cOpening)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    fun getOpeningByIdx(idx: String): Optional<WellOpeningDTO> {
        val opening = wellOpeningRepository.findByIdx(idx.uppercase())
        return opening.map { it.toDto() }
    }

    fun searchOpening(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellOpeningDTO> {
        if (searchKeyword.isNullOrEmpty()) {
            return wellOpeningRepository.findAll(pageable).map { it.toDto() }
        }

        return wellOpeningRepository.findAll(
            WellServiceUtil.Specification<WellOpeningEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }

}