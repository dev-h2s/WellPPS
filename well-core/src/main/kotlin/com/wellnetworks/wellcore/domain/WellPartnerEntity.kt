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
    var Idx : UUID,

    @Column(name="user_idx", unique = true, nullable = false)
    var UserIdx: UUID,

    @Column(name="tbl_id", length = 16, nullable = false)
    var TableID: String,

    @Column(name="cname", length = 64, nullable = false)
    var CompanyName: String,

    @Column(name="ctype", nullable = false)
    @Convert(converter = CompanyTypeToIndexConverter::class)
    var CompanyType: CompanyType,

    @Column(name="cgrp")
    var CompanyGroup: Byte,

    @Column(name="tax_num", length = 16, nullable = false)
    var TaxNumber: String,

    @Column(name="tax_mail", length = 320, nullable = false)
    var TaxEmail: String,

    @Column(name="ctel", length = 16, nullable = false)
    var TelephoneOffice: String,

    @Column(name="cmail", length = 320, nullable = false)
    var EmailOffice: String,

    @Column(name="rate")
    @Convert(converter = RateTypeToIndexConverter::class)
    var Rate: RateType,

    @Column(name="con_person", length = 64, nullable = false)
    var ContactPerson: String,

    @Column(name="use_api", nullable = false)
    var UseAPI: Boolean,

    @Column(name="cstate")
    @Convert(converter = CompanyStateTypeToIndexConverter::class)
    var CompanyState: CompanyStateType,

    @Column(name="clevel")
    var CompanyLevel: Byte,

    @Column(name="parent_org")
    var ParentOrganization: UUID,

    @Column(name="child_org")
    var ChildOrganization: UUID,

    @Column(name="ceo_name", length = 64)
    var CEOName: String,

    @Column(name="ceo_tel", length = 16)
    var CEOTelephone: String,

    @Column(name="phone_cert")
    var CertificationPhone: Boolean,

    @Column(name="email_cert")
    var CertificationEmail: Boolean,

    @Column(name="tax_addr1", length = 255)
    var TaxAddress1: String,

    @Column(name="tax_addr2", length = 255)
    var TaxAddress2: String,

    @Column(name="caddr1", length = 255)
    var CompanyAddress1: String,

    @Column(name="caddr2", length = 255)
    var CompanyAddress2: String,

    @Column(name="priconsent", length = 16)
    var PriorConsent: String,

    @Column(name="contact_type")
    @Convert(converter = ContactTypeToIndexConverter::class)
    var ContactType: ContactType,

    @Column(name="agree", nullable = false)
    @Convert(converter = AgreeTypeToIndexConverter::class)
    var AgreeTerms: AgreeType,

    @Column(name="agree_dt", nullable = false)
    var AgreeTermsDatetime: ZonedDateTime,

    @Column(name="agree_mod", nullable = false)
    var AgreeTermsModifyDatetime: ZonedDateTime,

    @Column(name="b_name", length = 64)
    var BankName: String,

    @Column(name="b_account", length = 64)
    var BankAccount: String,

    @Column(name="b_holder", length = 64)
    var BankHolder: String,

    @Column(name="memo", length = 255)
    var AdminMemo: String,

    @Column(name="j_prog")
    @Convert(converter = ContactProgressTypeToIndexConverter::class)
    var JoinProgress: ContactProgressType,

    @Column(name="contact_phone", length = 16)
    var ContactPhone: String,

    @Column(name="contact_dt")
    var ContactDatetime: ZonedDateTime,

    @Column(name="contact_addr1", length = 255)
    var ContactAddress1: String,

    @Column(name="contact_addr2", length = 255)
    var ContactAddress2: String,

    @Column(name="contact_prog")
    @Convert(converter = ContactProgressTypeToIndexConverter::class)
    var ContactProgress: ContactProgressType,

    @Column(name="contact_rej")
    @Convert(converter = ContactRejectTypeToIndexConverter::class)
    var ContectReject: ContactRejectType,

    @Column(name="contect_approver", length = 64)
    var ContactApprover: String,

    @Column(name="contect_regdt")
    var ContactRegisterDatetime: ZonedDateTime,

    @Column(name="contect_moddt")
    var ContactModifyDatetime: ZonedDateTime,
    ): BaseEntity() {

    fun getWellPartnerDTO(): WellPartnerDTO {
        return WellPartnerDTO(
            Idx = this.Idx,
            UserIdx = this.UserIdx,
            TableID = this.TableID,
            Company_Name = this.CompanyName,
            Company_Type = this.CompanyType,
            Company_Group = this.CompanyGroup,
            Company_State = this.CompanyState,
            Company_Level = this.CompanyLevel,
            Company_Address1 = this.CompanyAddress1,
            Company_Address2 = this.CompanyAddress2,
            Tax_Number = this.TaxNumber,
            Tax_Email = this.TaxEmail,
            Tax_Address1 = this.TaxAddress1,
            Tax_Address2 = this.TaxAddress2,
            Office_Telephone = this.TelephoneOffice,
            Office_Email = this.EmailOffice,
            Rate = this.Rate,
            Contact_Person = this.ContactPerson,
            Contact_Type = this.ContactType,
            Contact_Phone = this.ContactPhone,
            Contact_Datetime = this.ContactDatetime,
            Contact_Address1 = this.ContactAddress1,
            Contact_Address2 = this.ContactAddress2,
            Contact_Progress = this.ContactProgress,
            Contact_Reject = this.ContectReject,
            Contact_Approver = this.ContactApprover,
            Contact_Register_Datetime = this.ContactRegisterDatetime,
            Contact_Modify_Datetime = this.ContactModifyDatetime,
            Use_API = this.UseAPI,
            Organization_Parent = this.ParentOrganization,
            Organization_Child = this.ChildOrganization,
            CEO_Name = this.CEOName,
            CEO_Telephone = this.CEOTelephone,
            Certification_Phone = this.CertificationPhone,
            Certification_Email = this.CertificationEmail,
            Prior_Consent = this.PriorConsent,
            Agree_Terms = this.AgreeTerms,
            Agree_Terms_Datetime = this.AgreeTermsDatetime,
            Agree_Terms_Modify_Datetime = this.AgreeTermsModifyDatetime,
            Bank_Name = this.BankName,
            Bank_Account = this.BankAccount,
            Bank_Holder = this.BankHolder,
            Admin_Memo = this.AdminMemo,
            Join_Progress = this.JoinProgress,
            Modify_Datetime = this.ModifyDatetime,
            Register_Datetime = this.RegisterDatetime,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerEntity

        return Idx != null && Idx == other.Idx;
    }

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun toString(): String {
        return CompanyName;
    }
}