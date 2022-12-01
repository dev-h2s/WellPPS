package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.dto.WellPartnerDTO
import com.wellnetworks.wellcore.repository.WellPartnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class WellPartnerService {
    @Autowired
    lateinit var wellPartnerRepository: WellPartnerRepository

    fun getPartnerByCompanyName(companyName: String): Optional<WellPartnerDTO> {
        val partner = wellPartnerRepository.findByCompanyName(companyName)
        return partner.map { it.getWellPartnerDTO() }
    }

}