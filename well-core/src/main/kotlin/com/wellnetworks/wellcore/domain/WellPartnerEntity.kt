package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.*
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTO
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "partner_tb", indexes =
 [Index(name = "IX_user_idx", columnList = "user_idx", unique = true),
 ])

data class WellPartnerEntity (
    @Id
    @Column(name="idx", unique = true, nullable = false)
    var idx : UUID,

    @Column(name="user_idx", unique = true, nullable = false)
    var userIdx: UUID,

    @Column(name="tbl_id", length = 16, nullable = false)
    var tableID: String,

    @Column(name="cname", length = 64, nullable = false)
    var companyName: String,

    @Column(name="ctype", nullable = false)
    @Convert(converter = CompanyTypeToIndexConverter::class)
    var companyType: CompanyType,

    @Column(name="cgrp")
    var companyGroup: Byte,

    @Column(name="tax_num", length = 16, nullable = false)
    var taxNumber: String,

    @Column(name="tax_mail", length = 320, nullable = false)
    var taxEmail: String,

    @Column(name="ctel", length = 16, nullable = false)
    var telephoneOffice: String,

    @Column(name="cmail", length = 320, nullable = false)
    var emailOffice: String,

    @Column(name="rate")
    @Convert(converter = RateTypeToIndexConverter::class)
    var rate: RateType,

    @Column(name="con_person", length = 64, nullable = false)
    var contactPerson: String,

    @Column(name="use_api", nullable = false)
    var useAPI: Boolean,

    @Column(name="cstate")
    @Convert(converter = CompanyStateTypeToIndexConverter::class)
    var companyState: CompanyStateType,

    @Column(name="clevel")
    var companyLevel: Byte,

    @Column(name="parent_org")
    var parentOrganization: UUID,

    @Column(name="child_org")
    var childOrganization: UUID,

    @Column(name="ceo_name", length = 64)
    var ceoName: String,

    @Column(name="ceo_tel", length = 16)
    var ceoTelephone: String,

    @Column(name="phone_cert")
    var certificationPhone: Boolean,

    @Column(name="email_cert")
    var certificationEmail: Boolean,

    @Column(name="tax_addr1", length = 255)
    var taxAddress1: String,

    @Column(name="tax_addr2", length = 255)
    var taxAddress2: String,

    @Column(name="caddr1", length = 255)
    var companyAddress1: String,

    @Column(name="caddr2", length = 255)
    var companyAddress2: String,

    @Column(name="priconsent", length = 16)
    var priorConsent: String,

    @Column(name="contact_type")
    @Convert(converter = ContactTypeToIndexConverter::class)
    var contactType: ContactType,

    @Column(name="agree", nullable = false)
    @Convert(converter = AgreeTypeToIndexConverter::class)
    var agreeTerms: AgreeType,

    @Column(name="agree_dt", nullable = false)
    var agreeTermsDatetime: ZonedDateTime,

    @Column(name="agree_mod", nullable = false)
    var agreeTermsModifyDatetime: ZonedDateTime,

    @Column(name="b_name", length = 64)
    var bankName: String,

    @Column(name="b_account", length = 64)
    var bankAccount: String,

    @Column(name="b_holder", length = 64)
    var bankHolder: String,

    @Column(name="memo", length = 255)
    var adminMemo: String,

    @Column(name="j_prog")
    @Convert(converter = ContactProgressTypeToIndexConverter::class)
    var joinProgress: ContactProgressType,

    @Column(name="contact_phone", length = 16)
    var contactPhone: String,

    @Column(name="contact_dt")
    var contactDatetime: ZonedDateTime,

    @Column(name="contact_addr1", length = 255)
    var contactAddress1: String,

    @Column(name="contact_addr2", length = 255)
    var contactAddress2: String,

    @Column(name="contact_prog")
    @Convert(converter = ContactProgressTypeToIndexConverter::class)
    var contactProgress: ContactProgressType,

    @Column(name="contact_rej")
    @Convert(converter = ContactRejectTypeToIndexConverter::class)
    var contectReject: ContactRejectType,

    @Column(name="contect_approver", length = 64)
    var contactApprover: String,

    @Column(name="contect_regdt")
    var contactRegisterDatetime: ZonedDateTime,

    @Column(name="contect_moddt")
    var contactModifyDatetime: ZonedDateTime,
    ): BaseEntity() {

    fun getWellPartnerDTO(): WellPartnerDTO {
        return WellPartnerDTO(
            Idx = this.idx,
            UserIdx = this.userIdx,
            TableID = this.tableID,
            Company_Name = this.companyName,
            Company_Type = this.companyType,
            Company_Group = this.companyGroup,
            Company_State = this.companyState,
            Company_Level = this.companyLevel,
            Company_Address1 = this.companyAddress1,
            Company_Address2 = this.companyAddress2,
            Tax_Number = this.taxNumber,
            Tax_Email = this.taxEmail,
            Tax_Address1 = this.taxAddress1,
            Tax_Address2 = this.taxAddress2,
            Office_Telephone = this.telephoneOffice,
            Office_Email = this.emailOffice,
            Rate = this.rate,
            Contact_Person = this.contactPerson,
            Contact_Type = this.contactType,
            Contact_Phone = this.contactPhone,
            Contact_Datetime = this.contactDatetime,
            Contact_Address1 = this.contactAddress1,
            Contact_Address2 = this.contactAddress2,
            Contact_Progress = this.contactProgress,
            Contact_Reject = this.contectReject,
            Contact_Approver = this.contactApprover,
            Contact_Register_Datetime = this.contactRegisterDatetime,
            Contact_Modify_Datetime = this.contactModifyDatetime,
            Use_API = this.useAPI,
            Organization_Parent = this.parentOrganization,
            Organization_Child = this.childOrganization,
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
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerEntity

        return idx != null && idx == other.idx;
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    override fun toString(): String {
        return companyName;
    }
}