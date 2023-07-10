package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellOpeningEntity
import com.wellnetworks.wellcore.domain.dto.WellOpeningDTO
import com.wellnetworks.wellcore.repository.WellOpeningRepository
import org.springframework.beans.factory.annotation.Autowired
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

}