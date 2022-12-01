package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.repository.WellPartnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WellPartnerService {
    @Autowired
    lateinit var wellPartnerReadable: WellPartnerRepository



}