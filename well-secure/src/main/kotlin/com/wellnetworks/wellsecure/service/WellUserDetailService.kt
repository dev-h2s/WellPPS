package com.wellnetworks.wellsecure.service

import com.wellnetworks.wellcore.repository.WellUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
@ComponentScan("com.wellnetworks.wellcore.repository")
class WellUserDetailService: UserDetailsService {

    @Autowired(required = true)
    lateinit var wellUserRepository: WellUserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        return wellUserRepository.findByUserid(username).get()
    }
}