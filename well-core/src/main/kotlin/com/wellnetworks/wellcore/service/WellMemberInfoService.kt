package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellMemberInfoEntity
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.repository.WellMemberInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
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
    fun createMember(member: WellMemberInfoDTOCreate): UUID? {
        val uuidMember = UUID.randomUUID()
        val createMember = WellMemberInfoEntity(uuidMember,
            member.User_Idx, member.Table_ID, member.Current_Employment, member.Name, member.Email,
            member.Phone_Private, member.Phone_Work, member.Registration_Number,
            member.Department, member.Job_Position, member.Level,
            member.Home_Address1, member.Home_Address2, member.Bank_Name, member.Bank_Account, member.Bank_Holder,
            member.Employment_State, member.Job_Type, member.Certification_Phone, member.Certification_Email,
            member.Entry_Datetime, member.Employment_Quit_Datetime, member.Employment_Quit_Type,
            member.Access, member.Memo)
        try{
            wellMemberInfoRepository.save(createMember)
        } catch (e: IllegalArgumentException){
            return null
        }
        return uuidMember

    }

}