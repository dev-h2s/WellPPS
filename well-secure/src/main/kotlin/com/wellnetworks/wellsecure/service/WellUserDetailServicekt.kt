package com.wellnetworks.wellsecure.service

import com.wellnetworks.wellcore.repository.WellUserRepository
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
@ComponentScan("com.wellnetworks.wellcore.repository")
class WellUserDetailServicekt(private var wellUserRepository: WellUserRepository): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = wellUserRepository.findByUserID(username)
            .orElseThrow {
                UsernameNotFoundException("The username $username doesn't exist")
            }

        return User(user.username, user.password, user.authorities)
    }
}