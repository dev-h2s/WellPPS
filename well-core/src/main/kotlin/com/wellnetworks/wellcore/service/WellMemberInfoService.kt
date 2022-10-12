package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellMemberInfo
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoCreateDTO
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import com.wellnetworks.wellcore.repository.WellMemberInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.lang.reflect.Member
import java.util.Optional
import java.util.UUID
import javax.transaction.Transactional

@Component
class WellMemberInfoService {

    @Autowired
    lateinit var wellMemberInfoRepository: WellMemberInfoRepository

    fun getMemberByMemberName(name: String): Optional<WellMemberInfoDTO> {
        val member = wellMemberInfoRepository.findByName(name)
        return  member.map { it.getWellMemberInfoDTO() }
    }

    @Transactional
    fun createMember(member: WellMemberInfoCreateDTO): UUID? {
        val uuidMember = UUID.randomUUID()
        val createMember = WellMemberInfo(uuidMember, member.user_idx, member.table_id, member.belong, member.name, member.email,
            member.phone, member.well_phone, member.jumin, member.dep, member.pos, member.level, member.addr1, member.addr2, member.bank_name,
            member.b_account, member.b_holder, member.status, member.type, member.phone_cert, member.email_cert, member.entry_dt, member.retire_dt,
            member.retire_type, member.access, member.memo)
        try{
            wellMemberInfoRepository.save(createMember)
        } catch (e: IllegalArgumentException){
            return null
        }
        return uuidMember

    }

}