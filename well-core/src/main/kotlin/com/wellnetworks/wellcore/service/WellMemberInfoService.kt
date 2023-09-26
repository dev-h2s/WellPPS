package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellMemberInfoEntity
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.EmploymentQuitType
import com.wellnetworks.wellcore.domain.enums.PermissionKey
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

    // @Autowired 어노테이션은 스프링 프레임워크에서 자동으로 의존성 주입을 수행하는데 사용
    // 이것을 통해 해당 필드들은 스프링 컨테이너에서 관리되는 빈(Bean) 객체로 자동으로 주입
    // 주입된 빈은 클래스 내에서 사용할 수 있게 됨
    // lateinit은 Kotlin에서 사용되는 한정자로, 변수 초기화를 늦추는 기능을 제공
    // 코드에서 이 변수들을 사용하기 위해서는 스프링 프레임워크가 해당 변수들을 주입하고 초기화해야함
    @Autowired
    lateinit var wellMemberInfoRepository: WellMemberInfoRepository

    @Autowired
    private lateinit var wellUserService: WellUserService

    @Autowired
    private lateinit var wellFileStorageService: WellFileStorageService

    @Autowired
    private lateinit var wellUserRepository: WellUserRepository

    // 주어진 회원 ID에 해당하는 회원 정보를 가져오는 함수
    fun getMemberByIdx(idx: String): Optional<WellMemberInfoDTO> {
        val member = wellMemberInfoRepository.findByIdx(idx.uppercase())
        return member.map { it.toDto() }
    }
    // 회원 정보를 검색하고 페이지별로 반환하는 함수
    fun searchMember(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellMemberInfoDTO> {
        if (searchKeyword.isNullOrEmpty()) {
            return wellMemberInfoRepository.findAll(pageable).map { it.toDto() }
        }

        return wellMemberInfoRepository.findAll(
            WellServiceUtil.Specification<WellMemberInfoEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }

    // 주어진 회원 이름에 해당하는 회원 정보를 가져오는 함수
    fun getMemberByMemberName(name: String): Optional<WellMemberInfoDTO> {
        val member = wellMemberInfoRepository.findByName(name)
        return  member.map { it.toDto() }
    }
    // 새 회원 정보를 생성하는 함수
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
   //
    // updateMember: 회원 정보를 업데이트하고 파일을 업로드하는 함수
    @Transactional(rollbackFor = [Exception::class])
    fun updateMember(member: WellMemberDTOUpdate, files: List<MultipartFile>?): Boolean {
        try {
            val currentEntity = wellMemberInfoRepository.findByIdx(member.Idx.uppercase()).orElse(null) ?: return false

            val permissions: List<String> = listOf (
                PermissionKey.MEMBER_SCREENING,
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

    // deleteMemberById: 주어진 회원 ID에 해당하는 회원 정보를 삭제하는 함수
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