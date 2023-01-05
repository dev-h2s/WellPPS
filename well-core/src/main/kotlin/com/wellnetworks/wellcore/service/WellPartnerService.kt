package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellPartnerEntity
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTO
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTOCreate
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTOSignup
import com.wellnetworks.wellcore.domain.dto.WellUserDTOCreate
import com.wellnetworks.wellcore.domain.enums.*
import com.wellnetworks.wellcore.repository.WellPartnerRepository
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
import javax.persistence.criteria.Predicate


@Component
class WellPartnerService {
    @Autowired
    private lateinit var wellPartnerRepository: WellPartnerRepository

    @Autowired
    private lateinit var wellUserService: WellUserService

    @Autowired
    private lateinit var wellFileStorageService: WellFileStorageService

    fun getPartnerByIdx(idx: String): Optional<WellPartnerDTO> {
        val partner = wellPartnerRepository.findByIdx(idx)
        return partner.map { it.toDto() }
    }

    fun searchPartner(pageable: Pageable, searchKeyword: List<SearchCriteria>? = null): Page<WellPartnerDTO> {
        if (searchKeyword.isNullOrEmpty()) {
            return wellPartnerRepository.findAll(pageable).map { it.toDto() }
        }

        return wellPartnerRepository.findAll(
            WellServiceUtil.Specification<WellPartnerEntity>(searchKeyword), pageable)
            .map { it.toDto() }
    }

    // 회원가입을 통한 파트너 등록
    @Transactional(rollbackFor = [Exception::class])
    fun signupPartner(partner: WellPartnerDTOSignup, files: List<MultipartFile>): Boolean {
        var userID = UUID.randomUUID().toString().uppercase()

        val createPartner = WellPartnerEntity(
            userID, null, TableIDList.PARTNER.TableID,
            null, CompanyType.COMPANY_TYPE_UNKNOWN, null,
            null, partner.Tax_Number, partner.Tax_Email,
            null, null, RateType.RATE_TYPE_UNKNOWN, null, false,
            CompanyStateType.COMPANY_STATE_TYPE_TEMPORARY_REGISTRATION,
            null, null, null,
            partner.CEO_Name, partner.CEO_Telephone, null, false, false,
            null, null, null, null, null, ContactType.CONTACT_TYPE_UNKNOWN,
            partner.Agree_Terms, ZonedDateTime.now(), null,
            null, null, null, null,
            ContactProgressType.CONTACT_PROGRESS_TYPE_WAITING,
            partner.CEO_Telephone, null, null, null,
            ContactProgressType.CONTACT_PROGRESS_TYPE_UNKNOWN,
            ContactRejectType.CONTACT_REJECT_TYPE_UNKNOWN,
            null, null, null
        )

        val permissions: List<String> = listOf (
            PermissionList.PERMISSION_SINGUP_SCREENING.PermitssionKey,
        )

        try {
            for(file in files) {
                val fileID = wellFileStorageService.saveFile(TableIDList.PARTNER.TableID, userID, null, false, permissions, file)
                if (fileID == null) return false

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
    fun creaetPartner(user: WellUserDTOCreate, partner: WellPartnerDTOCreate, files: List<MultipartFile>): Boolean {
        try {
            var userIdx: String = wellUserService.createUser(user) ?: throw Exception("사용자 데이터 생성에 실패하였습니다.")

            val createPartner = WellPartnerEntity(
                userIdx, partner.P_Code, TableIDList.PARTNER.TableID,
                partner.Company_Name, partner.Company_Type, partner.Company_Group,
                null, partner.Tax_Number, partner.Tax_Email,
                partner.Office_Telephone, partner.Office_Email, partner.Rate, partner.Contact_Person, partner.Use_API,
                partner.Company_State, partner.Company_Level, partner.Organization_Parent, partner.Organization_Child,
                partner.CEO_Name, partner.CEO_Telephone, null, partner.Certification_Phone, partner.Certification_Email,
                partner.Tax_Address1, partner.Tax_Address2, partner.Company_Address1, partner.Company_Address2, partner.Prior_Consent, partner.Contact_Type,
                partner.Agree_Terms, ZonedDateTime.now(), null,
                partner.Bank_Name, partner.Bank_Account, partner.Bank_Holder, partner.Admin_Memo,
                partner.Contact_Progress,
                partner.CEO_Telephone, partner.Contact_Datetime, partner.Contact_Address1, partner.Contact_Address2,
                partner.Contact_Progress, partner.Contact_Reject, partner.Contact_Approver, partner.Contact_Register_Datetime, partner.Contact_Modify_Datetime
            )

            val permissions: List<String> = listOf (
                PermissionList.PERMISSION_SINGUP_SCREENING.PermitssionKey,
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
    fun updatePartner(partner: WellPartnerDTO, files: List<MultipartFile>): Boolean {
        try {
            val currentEntity = wellPartnerRepository.findByIdx(partner.Idx).orElse(null) ?: return false

            val permissions: List<String> = listOf (
                PermissionList.PERMISSION_SINGUP_SCREENING.PermitssionKey,
            )

            // 파일이 변경되었으면 삭제 후 새 파일 업로드.
            for (file in files) {
                var fileID: String? = wellFileStorageService.saveFile(TableIDList.PARTNER.TableID, currentEntity.idx, null, false, permissions, file)
                    ?: throw Exception("파트너 업데이트에 실패하였습니다.")

                if (file.originalFilename == partner.Tax_Registration_DocumentFileName) {
                    if (currentEntity.taxRegistrationDocFileIdx != null)   wellFileStorageService.deleteFile(currentEntity.taxRegistrationDocFileIdx!!)
                    currentEntity.taxRegistrationDocFileIdx = fileID
                } else if (file.originalFilename == partner.CEO_IDCard_FileName) {
                    if (currentEntity.ceoIDCardFileIdx != null)   wellFileStorageService.deleteFile(currentEntity.ceoIDCardFileIdx!!)
                    currentEntity.ceoIDCardFileIdx = fileID
                }
            }

            currentEntity.fromDto(partner)
            currentEntity.modifyDatetime = ZonedDateTime.now()

            wellPartnerRepository.save(currentEntity)
        } catch (e: Exception) {
            return false
        }

        return true
    }
}