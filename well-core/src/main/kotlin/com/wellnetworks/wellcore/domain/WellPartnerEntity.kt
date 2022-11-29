package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.*
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