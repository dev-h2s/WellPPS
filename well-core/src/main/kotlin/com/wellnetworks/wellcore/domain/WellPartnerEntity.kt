package com.wellnetworks.wellcore.domain
// 거래처 정보
import com.wellnetworks.wellcore.domain.converter.*
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTO
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTOUpdate
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "partner_tb", indexes =
 [Index(name = "IX_pcode", columnList = "pcode", unique = true),
 ])

data class WellPartnerEntity (
    @Id
    @Column(name="idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx : String,

    @Column(name="pcode", length = 32, unique = true, nullable = true)
    var pCode: String?,

    @Column(name="tbl_id", length = 16, nullable = false)
    var tableID: String,

    @Column(name="cname", length = 64, nullable = true)
    var companyName: String?,

    @Column(name="ctype", nullable = false)
    @Convert(converter = CompanyTypeToIndexConverter::class)
    var companyType: CompanyType,

    @Column(name="cgrp", nullable = true)
    var companyGroup: Byte?,

    @Column(name="tax_regdoc", nullable = true)
    var taxRegistrationDocFileIdx: String?,

    @Column(name="tax_num", length = 16, nullable = true)
    var taxNumber: String?,

    @Column(name="tax_mail", length = 320, nullable = true)
    var taxEmail: String?,

    @Column(name="contract_regdoc", nullable = true)
    var contractDocFileIdx: String?,

    @Column(name="ctel", length = 16, nullable = true)
    var telephoneOffice: String?,

    @Column(name="cmail", length = 320, nullable = true)
    var emailOffice: String?,

    @Column(name="rate")
    @Convert(converter = RateTypeToIndexConverter::class)
    var rate: RateType,

    @Column(name="con_person", length = 64, nullable = true)
    var contactPerson: String?,

    @Column(name="use_api", columnDefinition = "bit", nullable = false)
    var useAPI: Boolean,

    @Column(name="cstate")
    @Convert(converter = CompanyStateTypeToIndexConverter::class)
    var companyState: CompanyStateType,

    @Column(name="clevel", nullable = true)
    var companyLevel: Byte?,

    @Column(name="parent_org", nullable = true)
    var parentOrganization: String?,

    @Column(name="child_org", nullable = true)
    var childOrganization: String?,

    @Column(name="ceo_name", length = 64)
    var ceoName: String,

    @Column(name="ceo_tel", length = 16)
    var ceoTelephone: String,

    @Column(name="ceo_idcard", nullable = true)
    var ceoIDCardFileIdx: String?,

    @Column(name="phone_cert", columnDefinition = "bit")
    var certificationPhone: Boolean,

    @Column(name="email_cert", columnDefinition = "bit")
    var certificationEmail: Boolean,

    @Column(name="tax_addr1", length = 255, nullable = true)
    var taxAddress1: String?,

    @Column(name="tax_addr2", length = 255, nullable = true)
    var taxAddress2: String?,

    @Column(name="caddr1", length = 255, nullable = true)
    var companyAddress1: String?,

    @Column(name="caddr2", length = 255, nullable = true)
    var companyAddress2: String?,

    @Column(name="priconsent", length = 16, nullable = true)
    var priorConsent: String?,

    @Column(name="contact_type")
    @Convert(converter = ContactTypeToIndexConverter::class)
    var contactType: ContactType,

    @Column(name="agree", nullable = false)
    @Convert(converter = AgreeTypeToIndexConverter::class)
    var agreeTerms: AgreeType,

    @Column(name="agree_dt", nullable = false)
    var agreeTermsDatetime: ZonedDateTime,

    @Column(name="agree_mod", nullable = true)
    var agreeTermsModifyDatetime: ZonedDateTime?,

    @Column(name="b_name", length = 64, nullable = true)
    var bankName: String?,

    @Column(name="b_account", length = 64, nullable = true)
    var bankAccount: String?,

    @Column(name="b_holder", length = 64, nullable = true)
    var bankHolder: String?,

    @Column(name="memo", nullable = true)
    var adminMemo: String?,

    @Column(name="j_prog")
    @Convert(converter = ContactProgressTypeToIndexConverter::class)
    var joinProgress: ContactProgressType,

    @Column(name="contact_phone", length = 16, nullable = true)
    var contactPhone: String?,

    @Column(name="contact_dt", nullable = true)
    var contactDatetime: ZonedDateTime?,

    @Column(name="contact_addr1", length = 255, nullable = true)
    var contactAddress1: String?,

    @Column(name="contact_addr2", length = 255, nullable = true)
    var contactAddress2: String?,

    @Column(name="contact_prog")
    @Convert(converter = ContactProgressTypeToIndexConverter::class)
    var contactProgress: ContactProgressType,

    @Column(name="contact_rej")
    @Convert(converter = ContactRejectTypeToIndexConverter::class)
    var contactReject: ContactRejectType,

    @Column(name="contact_approver", length = 64, nullable = true)
    var contactApprover: String?,

    @Column(name="contact_regdt", nullable = true)
    var contactRegisterDatetime: ZonedDateTime?,

    @Column(name="contact_moddt", nullable = true)
    var contactModifyDatetime: ZonedDateTime?,
    ): BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerEntity

        return idx != null && idx.uppercase() == other.idx.uppercase();
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    override fun toString(): String {
        return companyName ?: ""
    }


    fun toDto(): WellPartnerDTO = WellPartnerDTO(
        Idx = this.idx.uppercase(),
        P_Code = this.pCode,
        TableID = this.tableID,
        Company_Name = this.companyName,
        Company_Type = this.companyType,
        Company_Type_String = this.companyType.toString(),
        Company_Group = this.companyGroup,
        Company_State = this.companyState,
        Company_Level = this.companyLevel,
        Company_Address1 = this.companyAddress1,
        Company_Address2 = this.companyAddress2,
        Tax_RegistrationDocumentFileIdx = this.taxRegistrationDocFileIdx?.uppercase(),
        Tax_Number = this.taxNumber,
        Tax_Email = this.taxEmail,
        Tax_Address1 = this.taxAddress1,
        Tax_Address2 = this.taxAddress2,
        Office_Telephone = this.telephoneOffice,
        Office_Email = this.emailOffice,
        Rate = this.rate,
        Contract_DocFileIdx = this.contractDocFileIdx?.uppercase(),
        Contract_DocumentFileName = null,
        Contact_Person = this.contactPerson,
        Contact_Type = this.contactType,
        Contact_Phone = this.contactPhone,
        Contact_Datetime = this.contactDatetime,
        Contact_Address1 = this.contactAddress1,
        Contact_Address2 = this.contactAddress2,
        Contact_Progress = this.contactProgress,
        Contact_Reject = this.contactReject,
        Contact_Approver = this.contactApprover,
        Contact_Register_Datetime = this.contactRegisterDatetime,
        Contact_Modify_Datetime = this.contactModifyDatetime,
        Use_API = this.useAPI,
        Organization_Parent = this.parentOrganization?.uppercase(),
        Organization_Child = this.childOrganization?.uppercase(),
        CEO_IDCard_FileIdx = this.ceoIDCardFileIdx?.uppercase(),
        CEO_Name = this.ceoName,
        CEO_Telephone = this.ceoTelephone,
        Certification_Phone = this.certificationPhone,
        Certification_Email = this.certificationEmail,
        Prior_Consent = this.priorConsent,
        Agree_Terms = this.agreeTerms,
        Agree_Terms_Datetime = this.agreeTermsDatetime,
        Agree_Terms_Modify_Datetime = this.agreeTermsModifyDatetime,
        Bank_Name = this.bankName,
        Bank_Account = this.bankAccount,
        Bank_Holder = this.bankHolder,
        Admin_Memo = this.adminMemo,
        Join_Progress = this.joinProgress,
        Modify_Datetime = this.modifyDatetime,
        Register_Datetime = this.registerDatetime,
        CEO_IDCard_FileName = null,
        Tax_Registration_DocumentFileName = null,
    )

    fun fromDto(dto: WellPartnerDTO) {
        this.pCode = dto.P_Code
        this.tableID = dto.TableID
        this.companyName = dto.Company_Name
        this.companyType = dto.Company_Type
        this.companyGroup = dto.Company_Group
        this.taxRegistrationDocFileIdx = dto.Tax_RegistrationDocumentFileIdx?.uppercase()
        this.taxNumber = dto.Tax_Number
        this.taxEmail = dto.Tax_Email
        this.contractDocFileIdx = dto.Contract_DocFileIdx?.uppercase()
        this.telephoneOffice = dto.Office_Telephone
        this.emailOffice = dto.Office_Email
        this.rate = dto.Rate
        this.contactPerson = dto.Contact_Person
        this.useAPI = dto.Use_API
        this.companyState = dto.Company_State
        this.companyLevel = dto.Company_Level
        this.parentOrganization = dto.Organization_Parent?.uppercase()
        this.childOrganization = dto.Organization_Child?.uppercase()
        this.ceoName = dto.CEO_Name
        this.ceoTelephone = dto.CEO_Telephone
        this.ceoIDCardFileIdx = dto.CEO_IDCard_FileIdx?.uppercase()
        this.certificationPhone = dto.Certification_Phone
        this.certificationEmail = dto.Certification_Email
        this.taxAddress1 = dto.Tax_Address1
        this.taxAddress2 = dto.Tax_Address2
        this.companyAddress1 = dto.Company_Address1
        this.companyAddress2 = dto.Company_Address2
        this.priorConsent = dto.Prior_Consent
        this.contactType = dto.Contact_Type
        this.agreeTerms = dto.Agree_Terms
        this.agreeTermsDatetime = dto.Agree_Terms_Datetime
        this.agreeTermsModifyDatetime = dto.Agree_Terms_Modify_Datetime
        this.bankName = dto.Bank_Name
        this.bankAccount = dto.Bank_Account
        this.bankHolder = dto.Bank_Holder
        this.adminMemo = dto.Admin_Memo
        this.joinProgress = dto.Join_Progress
        this.contactPhone = dto.Contact_Phone
        this.contactDatetime = dto.Contact_Datetime
        this.contactAddress1 = dto.Contact_Address1
        this.contactAddress2 = dto.Contact_Address2
        this.contactProgress = dto.Contact_Progress
        this.contactReject = dto.Contact_Reject
        this.contactApprover = dto.Contact_Approver
        this.contactRegisterDatetime = dto.Contact_Register_Datetime
        this.contactModifyDatetime = dto.Contact_Modify_Datetime
    }

    fun updateDto(dto: WellPartnerDTOUpdate) {
        if (dto.Company_Name != null) this.companyName = dto.Company_Name
        if (dto.Company_Type != null) this.companyType = dto.Company_Type //거래처구분:딜러,판매점,충전점,기타제휴사,기관,영업팀
        if (dto.Company_Group != null) this.companyGroup = dto.Company_Group
        if (dto.Rate != null) this.rate = dto.Rate //충전할인율:개통점,충전점,API
        if (dto.Contact_Person != null) this.contactPerson = dto.Contact_Person //영업담당자
        if (dto.Use_API != null) this.useAPI = dto.Use_API
        if (dto.Contact_Datetime != null) this.contactDatetime = dto.Contact_Datetime //가입승인일자
        if (dto.Company_State != null) this.companyState = dto.Company_State //거래유무
        if (dto.Company_Level != null) this.companyLevel = dto.Company_Level
        if (dto.Organization_Parent != null) this.parentOrganization = dto.Organization_Parent?.uppercase() //상부점
        if (dto.Organization_Child != null) this.childOrganization = dto.Organization_Child?.uppercase() //하부점
        if (dto.CEO_Name != null) this.ceoName = dto.CEO_Name
        if (dto.CEO_Telephone != null) this.ceoTelephone = dto.CEO_Telephone
        if (dto.Office_Telephone != null) this.telephoneOffice = dto.Office_Telephone
        if (dto.Office_Email != null) this.emailOffice = dto.Office_Email
        if (dto.Certification_Phone != null) this.certificationPhone = dto.Certification_Phone
        if (dto.Certification_Email != null) this.certificationEmail = dto.Certification_Email
        if (dto.Bank_Account != null) this.bankAccount = dto.Bank_Account
        if (dto.Bank_Holder != null) this.bankHolder = dto.Bank_Holder
        if (dto.Bank_Name != null) this.bankName = dto.Bank_Name
        if (dto.Tax_Number != null) this.taxNumber = dto.Tax_Number
        if (dto.Tax_Email != null) this.taxEmail = dto.Tax_Email
        if (dto.Prior_Consent != null) this.priorConsent = dto.Prior_Consent //사전승낙번호
        if (dto.Tax_Address1 != null) this.taxAddress1 = dto.Tax_Address1
        if (dto.Tax_Address2 != null) this.taxAddress2 = dto.Tax_Address2
        if (dto.Company_Address1 != null) this.companyAddress1 = dto.Company_Address1
        if (dto.Company_Address2 != null) this.companyAddress2 = dto.Company_Address2
        if (dto.Agree_Terms != null) this.agreeTerms = dto.Agree_Terms
        if (dto.Agree_Terms_Datetime != null) this.agreeTermsDatetime = dto.Agree_Terms_Datetime
        if (dto.Agree_Terms_Modify_Datetime != null) this.agreeTermsModifyDatetime = dto.Agree_Terms_Modify_Datetime
        if (dto.Tax_RegistrationDocumentFileIdx != null) this.taxRegistrationDocFileIdx = dto.Tax_RegistrationDocumentFileIdx?.uppercase()
        if (dto.Contract_DocFileIdx != null) this.contractDocFileIdx = dto.Contract_DocFileIdx?.uppercase()
        if (dto.CEO_IDCard_FileIdx != null) this.ceoIDCardFileIdx = dto.CEO_IDCard_FileIdx?.uppercase()
        if (dto.Admin_Memo != null) this.adminMemo = dto.Admin_Memo
        modifyDatetime = ZonedDateTime.now()
    }
}