package com.wellnetworks.wellsecure.service

import com.wellnetworks.wellcore.domain.WellUser
import com.wellnetworks.wellcore.repository.WellUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
@ComponentScan("com.wellnetworks.wellcore.repository")
class WellUserDetailService: UserDetailsService {

    @Autowired
    lateinit var wellUserRepository: WellUserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        return wellUserRepository.findByUserID(username).get()

    }
}