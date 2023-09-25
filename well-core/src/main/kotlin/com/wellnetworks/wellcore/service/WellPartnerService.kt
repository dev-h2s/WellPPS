package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellPartnerEntity
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.*
import com.wellnetworks.wellcore.repository.WellPartnerRepository
import com.wellnetworks.wellcore.repository.WellUserRepository
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.utils.WellServiceUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.ZonedDateTime
import java.util.*

// 컨트롤러에서 넘어온 요청 처리
@Component
// @Component : 클래스를 Spring 애플리케이션 컨텍스트에 빈으로 등록하는 역할
// WellPartnerService 클래스에 @Component 어노테이션이 적용되었기 때문에 이 클래스의 빈이 등록되고,
// 다른 컴포넌트나 서비스에서 WellPartnerService의 인스턴스를 주입받아 사용 가능
class WellPartnerService {
    @Autowired
    // WellPartnerRepository는 데이터베이스와 상호작용하기 위한 JPA와 같은 데이터 액세스 기술을 사용하는 데이터 저장소에 접근하는 데 사용
    private lateinit var wellPartnerRepository: WellPartnerRepository

    @Autowired
    private lateinit var wellUserService: WellUserService

    @Autowired
    private lateinit var wellFileStorageService: WellFileStorageService

    @Autowired
    private lateinit var wellUserRepository: WellUserRepository
    fun getPartnerByIdx(idx: String): Optional<WellPartnerDTO> {
        //파트너 정보 조회
        // 정보 조회 후 Optional<WellPartnerDTO> 형태로 반환
        val partner = wellPartnerRepository.findByIdx(idx)
        return partner.map { it.toDto() }
    }
    @Transactional(rollbackFor = [Exception::class])
    fun deletePartnerById(idx: String) {
        val partner = wellPartnerRepository.deleteByIdx(idx)
        var user = wellUserRepository.deleteByIdx(idx)

        if (partner.get() == 1 && user.get() == 1) {
            return
        }

        throw Exception("delete count not match.")
    }

    fun searchPartner(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellPartnerDTO> {
        if (searchKeyword.isNullOrEmpty()) {
            return wellPartnerRepository.findAll(pageable).map { it.toDto() }
        }

        return wellPartnerRepository.findAll(
            WellServiceUtil.Specification<WellPartnerEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }
    data class cTypeCount(val regCount: Long, val tempCount: Long, val watchCount: Long, val susCount: Long)

    fun companyTypeCount(): cTypeCount {
        val regCnt = wellPartnerRepository.countByCompanyState(CompanyStateType.COMPANY_STATE_TYPE_REGISTERED)
        val tmpCnt =
            wellPartnerRepository.countByCompanyState(CompanyStateType.COMPANY_STATE_TYPE_TEMPORARY_REGISTRATION)
        val watchCnt = wellPartnerRepository.countByCompanyState(CompanyStateType.COMPANY_STATE_TYPE_WATCH)
        val susCnt = wellPartnerRepository.countByCompanyState(CompanyStateType.COMPANY_STATE_TYPE_SUSPENSION)

        return cTypeCount(regCnt, tmpCnt, watchCnt, susCnt)
    }

    data class ptnUnattachedCount(val taxIdxCount:Long, val contractIdxCount:Long)
    fun partnerUnattachedTaxCount(): ptnUnattachedCount {
        val taxUnattachedCount = wellPartnerRepository.countByTaxRegistrationDocFileIdxIsNull()
        val contractUnattachedCount = wellPartnerRepository.countByContractDocFileIdxIsNull()

        return ptnUnattachedCount(taxUnattachedCount, contractUnattachedCount)
    }

    // 회원가입을 통한 파트너 등록
    @Transactional(rollbackFor = [Exception::class])
    fun signupPartner(user: WellUserDTOCreate, signup: WellPartnerDTOSignup, files: List<MultipartFile>): Boolean {

        var userID: String = wellUserService.createUser(user) ?: throw Exception("사용자 데이터 생성에 실패하였습니다.")

        val createPartner = WellPartnerEntity(
            userID, user.UserID, TableIDList.PARTNER.TableID,
            null, CompanyType.COMPANY_TYPE_UNKNOWN, null,
            null, signup.Tax_Number, signup.Tax_Email,
            null, null, null, RateType.RATE_TYPE_UNKNOWN, null, false,
            CompanyStateType.COMPANY_STATE_TYPE_TEMPORARY_REGISTRATION,
            null, null, null,
            signup.CEO_Name, signup.CEO_Telephone, null, false, false,
            null, null, null, null, null, signup.Contact_Type,
            signup.Agree_Terms, ZonedDateTime.now(), null,
            null, null, null, null,
            ContactProgressType.CONTACT_PROGRESS_TYPE_WAITING,
            signup.CEO_Telephone, null, null, null,
            ContactProgressType.CONTACT_PROGRESS_TYPE_UNKNOWN,
            ContactRejectType.CONTACT_REJECT_TYPE_UNKNOWN,
            null, null, null
        )

        val permissions: List<String> = listOf(
            PermissionKey.SIGNUP_SCREENING,
        )

        try {
            for(file in files) {
                val fileID = wellFileStorageService.saveFile(TableIDList.PARTNER.TableID, userID, null, false, permissions, file)
                if (fileID == null) return false

                if (file.originalFilename == signup.Tax_Registration_DocumentFileName) {
                    createPartner.taxRegistrationDocFileIdx = fileID
                } else if (file.originalFilename == signup.CEO_IDCard_FileName) {
                    createPartner.ceoIDCardFileIdx = fileID
                }
            }

            wellPartnerRepository.save(createPartner)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    fun creaetPartner(user: WellUserDTOCreate, partner: WellPartnerDTOCreate, files: List<MultipartFile>): Boolean {
        try {
            var userIdx: String = wellUserService.createUser(user) ?: throw Exception("사용자 데이터 생성에 실패하였습니다.")

            val createPartner = WellPartnerEntity(
                userIdx, partner.P_Code, TableIDList.PARTNER.TableID,
                partner.Company_Name, partner.Company_Type, partner.Company_Group,partner.Tax_Registration_DocumentFileName,
                partner.Tax_Number, partner.Tax_Email, null,
                partner.Office_Telephone, partner.Office_Email, partner.Rate, partner.Contact_Person, partner.Use_API,
                partner.Company_State, partner.Company_Level, partner.Organization_Parent, partner.Organization_Child,
                partner.CEO_Name, partner.CEO_Telephone, partner.CEO_IDCard_FileName, partner.Certification_Phone, partner.Certification_Email,
                partner.Tax_Address1, partner.Tax_Address2, partner.Company_Address1, partner.Company_Address2, partner.Prior_Consent, partner.Contact_Type,
                partner.Agree_Terms, ZonedDateTime.now(), null,
                partner.Bank_Name, partner.Bank_Account, partner.Bank_Holder, partner.Admin_Memo,
                partner.Contact_Progress,
                partner.CEO_Telephone, partner.Contact_Datetime, partner.Contact_Address1, partner.Contact_Address2,
                partner.Contact_Progress, partner.Contact_Reject, partner.Contact_Approver, partner.Contact_Register_Datetime, partner.Contact_Modify_Datetime
            )

            val permissions: List<String> = listOf (
                PermissionKey.MEMBER_SCREENING,
            )

            for(file in files) {
                var fileID: String? =
                    wellFileStorageService.saveFile(TableIDList.PARTNER.TableID, userIdx, null, false, permissions, file)
                        ?: return false

                if (file.originalFilename == partner.Tax_Registration_DocumentFileName) {
                    createPartner.taxRegistrationDocFileIdx = fileID
                } else if (file.originalFilename == partner.CEO_IDCard_FileName) {
                    createPartner.ceoIDCardFileIdx = fileID
                }
            }

            wellPartnerRepository.save(createPartner)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    fun updatePartner(partner: WellPartnerDTOUpdate, files: List<MultipartFile>?): Boolean {
        try {
            val currentEntity = wellPartnerRepository.findByIdx(partner.Idx.toString().uppercase()).orElse(null) ?: return false

            val permissions: List<String> = listOf (
                PermissionKey.MEMBER_SCREENING,
            )

            // 파일이 변경되었으면 삭제 후 새 파일 업로드.
            if (files != null) {
                for (file in files) {
                    var fileID: String? = wellFileStorageService.saveFile(
                        TableIDList.PARTNER.TableID,
                        currentEntity.idx,
                        null,
                        false,
                        permissions,
                        file
                    )
                        ?: throw Exception("파트너 업데이트에 실패하였습니다.")

                    if (file.originalFilename == partner.Tax_Registration_DocumentFileName) {
                        if (currentEntity.taxRegistrationDocFileIdx != null) wellFileStorageService.deleteFile(
                            currentEntity.taxRegistrationDocFileIdx!!
                        )
                        currentEntity.taxRegistrationDocFileIdx = fileID
                    } else if (file.originalFilename == partner.Contract_DocumentFileName) {
                        if (currentEntity.contractDocFileIdx != null) wellFileStorageService.deleteFile(currentEntity.contractDocFileIdx!!)
                        currentEntity.contractDocFileIdx = fileID
                    } else if (file.originalFilename == partner.CEO_IDCard_FileName) {
                        if (currentEntity.ceoIDCardFileIdx != null) wellFileStorageService.deleteFile(currentEntity.ceoIDCardFileIdx!!)
                        currentEntity.ceoIDCardFileIdx = fileID
                    }
                }
            }

            currentEntity.updateDto(partner)

            wellPartnerRepository.save(currentEntity)
        } catch (e: Exception) {
            return false
        }

        return true
    }
}