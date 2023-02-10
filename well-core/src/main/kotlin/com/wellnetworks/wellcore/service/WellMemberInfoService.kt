package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellMemberInfoEntity
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.EmploymentQuitType
import com.wellnetworks.wellcore.domain.enums.PermissionList
import com.wellnetworks.wellcore.domain.enums.TableIDList
import com.wellnetworks.wellcore.repository.WellMemberInfoRepository
import com.wellnetworks.wellcore.repository.WellUserRepository
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

    @Autowired
    private lateinit var wellUserRepository: WellUserRepository
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
                userIdx, TableIDList.MEMBER.TableID, member.Current_Employment, member.Name, member.Email ?:"",
                member.Phone_Private?:"", member.Phone_Work ?: "", member.Registration_Number ?:"",
                member.Department, member.Job_Position, member.Level ?: 0,
                member.Home_Address1 ?: "", member.Home_Address2 ?: "", member.Bank_Name ?: "",
                member.Bank_Account ?: "", member.Bank_Holder ?: "",
                member.Employment_State, member.Job_Type, member.Certification_Phone, member.Certification_Email,
                null, null,
                member.Employment_Quit_Type ?: EmploymentQuitType.EMPLOYMENT_QUIT_TYPE_UNKNOWN,
                member.Access, member.Member_File1_idx, member.Member_File2_idx, member.Member_File3_idx,
                member.Member_File4_idx, member.Member_File5_idx, member.Memo ?:""
            )

            wellMemberInfoRepository.save(createMember)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    fun updateMember(member: WellMemberDTOUpdate, files: List<MultipartFile>?): Boolean {
        try {
            val currentEntity = wellMemberInfoRepository.findByIdx(member.Idx.uppercase()).orElse(null) ?: return false

            val permissions: List<String> = listOf (
                PermissionList.PERMISSION_MEMBER_SCREENING.PermitssionKey,
            )

            // 파일이 변경되었으면 삭제 후 새 파일 업로드.
            if (files != null) {
                for (file in files) {
                    var fileID: String? = wellFileStorageService.saveFile(
                        TableIDList.MEMBER.TableID,
                        currentEntity.idx,
                        null,
                        false,
                        permissions,
                        file
                    )
                        ?: throw Exception("파트너 업데이트에 실패하였습니다.")

                    if (file.originalFilename == member.Member_File1_idx) {
                        if (currentEntity.file1Idx != null) wellFileStorageService.deleteFile(currentEntity.file1Idx!!)
                        currentEntity.file1Idx = fileID
                        } else if (file.originalFilename == member.Member_File2_idx) {
                            if (currentEntity.file2Idx != null) wellFileStorageService.deleteFile(currentEntity.file2Idx!!)
                            currentEntity.file2Idx = fileID
                        } else if (file.originalFilename == member.Member_File3_idx) {
                            if (currentEntity.file3Idx != null) wellFileStorageService.deleteFile(currentEntity.file3Idx!!)
                            currentEntity.file3Idx = fileID
                        }else if (file.originalFilename == member.Member_File4_idx) {
                        if (currentEntity.file4Idx != null) wellFileStorageService.deleteFile(currentEntity.file4Idx!!)
                            currentEntity.file4Idx = fileID
                        }else if (file.originalFilename == member.Member_File5_idx) {
                            if (currentEntity.file5Idx != null) wellFileStorageService.deleteFile(currentEntity.file5Idx!!)
                            currentEntity.file5Idx = fileID
                        }
                }
            }
            currentEntity.updateDto(member)
            currentEntity.modifyDatetime = ZonedDateTime.now()

            wellMemberInfoRepository.save(currentEntity)

        } catch (e: Exception) {
            return false
        }

        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    fun deleteMemberById(idx: String) {
        val member = wellMemberInfoRepository.deleteByIdx(idx)
        var user = wellUserRepository.deleteByIdx(idx)

        if (member.get() == 1 && user.get() == 1) {
            return
        }

        throw Exception("delete count not match.")
    }
}