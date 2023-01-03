package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellMemberInfoEntity
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTOCreate
import com.wellnetworks.wellcore.domain.dto.WellUserDTOCreate
import com.wellnetworks.wellcore.repository.WellMemberInfoRepository
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.utils.WellServiceUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.lang.IllegalArgumentException
import java.time.ZonedDateTime
import java.util.Optional
import java.util.UUID

@Component
class WellMemberInfoService {

    @Autowired
    lateinit var wellMemberInfoRepository: WellMemberInfoRepository

    @Autowired
    private lateinit var wellUserService: WellUserService

    @Autowired
    private lateinit var wellFileStorageService: WellFileStorageService

    fun getMemberByIdx(idx: String): Optional<WellMemberInfoDTO> {
        val member = wellMemberInfoRepository.findByIdx(idx.uppercase())
        return member.map { it.toDto() }
    }

    fun searchMember(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellMemberInfoDTO> {
        if (searchKeyword.isNullOrEmpty()) {
            return wellMemberInfoRepository.findAll(pageable).map { it.toDto() }
        }

        return wellMemberInfoRepository.findAll(
            WellServiceUtil.Specification<WellMemberInfoEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }

    fun getMemberByMemberName(name: String): Optional<WellMemberInfoDTO> {
        val member = wellMemberInfoRepository.findByName(name)
        return  member.map { it.toDto() }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun createMember(user: WellUserDTOCreate, member: WellMemberInfoDTOCreate): Boolean {
        try {
            val userIdx: String = wellUserService.createUser(user) ?: throw Exception("사용자 데이터 생성에 실패하였습니다.")

            val createMember = WellMemberInfoEntity(
                userIdx,
                member.Table_ID, member.Current_Employment, member.Name, member.Email,
                member.Phone_Private, member.Phone_Work, member.Registration_Number,
                member.Department, member.Job_Position, member.Level,
                member.Home_Address1, member.Home_Address2, member.Bank_Name, member.Bank_Account, member.Bank_Holder,
                member.Employment_State, member.Job_Type, member.Certification_Phone, member.Certification_Email,
                member.Entry_Datetime, member.Employment_Quit_Datetime, member.Employment_Quit_Type,
                member.Access, member.Memo
            )

            wellMemberInfoRepository.save(createMember)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    fun updateMember(member: WellMemberInfoDTO): Boolean {
        try {
            val currentEntity = wellMemberInfoRepository.findByIdx(member.Idx).orElse(null) ?: return false

            currentEntity.fromDto(member)
            currentEntity.modifyDatetime = ZonedDateTime.now()

            wellMemberInfoRepository.save(currentEntity)

        } catch (e: Exception) {
            return false
        }

        return true
    }
}