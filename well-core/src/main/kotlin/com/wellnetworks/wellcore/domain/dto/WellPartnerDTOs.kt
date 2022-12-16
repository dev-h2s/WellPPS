package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.UUID

enum class WellPartnerColumnsName(val columnsName: String) {
    Idx("idx"),
    P_Code("pCode"),
    TableID("tableID"),
    Company_Name("companyName"),
    Company_Type("companyType"),
    Company_Group("companyGroup"),
    Company_State("companyState"),
    Company_Level("companyLevel"),
    Company_Address1("companyAddress1"),
    Company_Address2("companyAddress2"),
    Tax_RegistrationDocumentFile("taxRegistrationDocFile"),
    Tax_Number("taxNumber"),
    Tax_Email("taxEmail"),
    Tax_Address1("taxAddress1"),
    Tax_Address2("taxAddress2"),
    Office_Telephone("telephoneOffice"),
    Office_Email("emailOffice"),
    Rate("rate"),
    Contact_Person("contactPerson"),
    Contact_Type("contactType"),
    Contact_Phone("contactPhone"),
    Contact_Datetime("contactDatetime"),
    Contact_Address1("contactAddress1"),
    Contact_Address2("contactAddress2"),
    Contact_Progress("contactProgress"),
    Contact_Reject("contectReject"),
    Contact_Approver("contactApprover"),
    Contact_Register_Datetime("contactRegisterDatetime"),
    Contact_Modify_Datetime("contactModifyDatetime"),
    Use_API("useAPI"),
    Organization_Parent("parentOrganization"),
    Organization_Child("childOrganization"),
    CEO_IDCard_File("ceoIDCardFile"),
    CEO_Name("ceoName"),
    CEO_Telephone("ceoTelephone"),
    Certification_Phone("certificationPhone"),
    Certification_Email("certificationEmail"),
    Prior_Consent("priorConsent"),
    Agree_Terms("agreeTerms"),
    Agree_Terms_Datetime("agreeTermsDatetime"),
    Agree_Terms_Modify_Datetime("agreeTermsModifyDatetime"),
    Bank_Name("bankName"),
    Bank_Account("bankAccount"),
    Bank_Holder("bankHolder"),
    Admin_Memo("adminMemo"),
    Join_Progress("joinProgress"),
    Modify_Datetime("modifyDatetime"),
    Register_Datetime("registerDatetime"),
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellPartnerDTO (
    @JsonProperty("idx")
    val Idx: UUID,
    @JsonProperty("p")
    val P_Code: String?,
    @JsonProperty("tid")
    val TableID: String,

    @JsonProperty("cnam")
    val Company_Name: String?,
    @JsonProperty("ctyp")
    val Company_Type: CompanyType,
    @JsonProperty("cgrp")
    val Company_Group: Byte?,
    @JsonProperty("cste")
    val Company_State: CompanyStateType,
    @JsonProperty("clvl")
    val Company_Level: Byte?,
    @JsonProperty("cad1")
    val Company_Address1: String?,
    @JsonProperty("cad2")
    val Company_Address2: String?,

    @JsonProperty("txrfn")
    val Tax_Registration_DocumentFileName: String?,
    @JsonProperty("txrd")
    val Tax_RegistrationDocumentFile: UUID?,
    @JsonProperty("txnm")
    val Tax_Number: String?,
    @JsonProperty("txem")
    val Tax_Email: String?,
    @JsonProperty("txa1")
    val Tax_Address1: String?,
    @JsonProperty("txa2")
    val Tax_Address2: String?,

    @JsonProperty("otel")
    val Office_Telephone: String?,
    @JsonProperty("oeml")
    val Office_Email: String?,
    @JsonProperty("rat")
    val Rate: RateType,

    @JsonProperty("ctps")
    val Contact_Person : String?,
    @JsonProperty("cttp")
    val Contact_Type: ContactType,
    @JsonProperty("ctph")
    val Contact_Phone: String?,
    @JsonProperty("ctdt")
    val Contact_Datetime: ZonedDateTime?,
    @JsonProperty("cta1")
    val Contact_Address1: String?,
    @JsonProperty("cta2")
    val Contact_Address2: String?,
    @JsonProperty("ctpg")
    val Contact_Progress: ContactProgressType,
    @JsonProperty("ctrj")
    val Contact_Reject: ContactRejectType,
    @JsonProperty("ctap")
    val Contact_Approver: String?,
    @JsonProperty("ctrd")
    val Contact_Register_Datetime: ZonedDateTime?,
    @JsonProperty("ctmd")
    val Contact_Modify_Datetime: ZonedDateTime?,
    @JsonProperty("uapi")
    val Use_API: Boolean,

    @JsonProperty("orgp")
    val Organization_Parent: UUID?,
    @JsonProperty("orgc")
    val Organization_Child: UUID?,

    @JsonProperty("ceoin")
    val CEO_IDCard_FileName: String?,
    @JsonProperty("ceoi")
    val CEO_IDCard_File: UUID?,
    @JsonProperty("ceon")
    val CEO_Name: String,
    @JsonProperty("ceot")
    val CEO_Telephone: String,

    @JsonProperty("crtp")
    val Certification_Phone: Boolean,
    @JsonProperty("crte")
    val Certification_Email: Boolean,

    @JsonProperty("pcst")
    val Prior_Consent: String?,

    @JsonProperty("agtm")
    val Agree_Terms: AgreeType,
    @JsonProperty("agtd")
    val Agree_Terms_Datetime: ZonedDateTime,
    @JsonProperty("agtm")
    val Agree_Terms_Modify_Datetime: ZonedDateTime?,

    @JsonProperty("bnam")
    val Bank_Name: String?,
    @JsonProperty("bacc")
    val Bank_Account: String?,
    @JsonProperty("bhld")
    val Bank_Holder: String?,

    @JsonProperty("admm")
    val Admin_Memo: String?,

    @JsonProperty("jpro")
    val Join_Progress: ContactProgressType,

    @JsonIgnore
    override var Modify_Datetime: ZonedDateTime,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime,
): BaseDTO(Modify_Datetime, Register_Datetime) {

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerDTO

        return Idx != null && Idx == other.Idx;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellPartnerDTOCreate @JsonCreator constructor (
    @JsonProperty("tid")
    val TableID: String,
    @JsonProperty("p")
    val P_Code: String,

    @JsonProperty("cnam")
    val Company_Name: String?,
    @JsonProperty("ctyp")
    val Company_Type: CompanyType,
    @JsonProperty("cgrp")
    val Company_Group: Byte?,
    @JsonProperty("cste")
    val Company_State: CompanyStateType,
    @JsonProperty("clvl")
    val Company_Level: Byte?,
    @JsonProperty("cad1")
    val Company_Address1: String?,
    @JsonProperty("cad2")
    val Company_Address2: String?,

    @JsonProperty("txrfn")
    val Tax_Registration_DocumentFileName: String,
    @JsonProperty("txrd")
    val Tax_Registration_DocumentFile: UUID,
    @JsonProperty("txnm")
    val Tax_Number: String?,
    @JsonProperty("txem")
    val Tax_Email: String?,
    @JsonProperty("txa1")
    val Tax_Address1: String?,
    @JsonProperty("txa2")
    val Tax_Address2: String?,

    @JsonProperty("otel")
    val Office_Telephone: String?,
    @JsonProperty("oeml")
    val Office_Email: String?,
    @JsonProperty("rat")
    val Rate: RateType,

    @JsonProperty("ctps")
    val Contact_Person : String?,
    @JsonProperty("cttp")
    val Contact_Type: ContactType,
    @JsonProperty("ctph")
    val Contact_Phone: String?,
    @JsonProperty("ctdt")
    val Contact_Datetime: ZonedDateTime?,
    @JsonProperty("cta1")
    val Contact_Address1: String?,
    @JsonProperty("cta2")
    val Contact_Address2: String?,
    @JsonProperty("ctpg")
    val Contact_Progress: ContactProgressType,
    @JsonProperty("ctrj")
    val Contact_Reject: ContactRejectType,
    @JsonProperty("ctap")
    val Contact_Approver: String?,
    @JsonProperty("ctrd")
    val Contact_Register_Datetime: ZonedDateTime?,
    @JsonProperty("ctmd")
    val Contact_Modify_Datetime: ZonedDateTime?,
    @JsonProperty("uapi")
    val Use_API: Boolean,

    @JsonProperty("orgp")
    val Organization_Parent: UUID?,
    @JsonProperty("orgc")
    val Organization_Child: UUID?,

    @JsonProperty("ceoin")
    val CEO_IDCard_FileName: String,
    @JsonProperty("ceoi")
    val CEO_IDCard_File: UUID,
    @JsonProperty("ceon")
    val CEO_Name: String,
    @JsonProperty("ceot")
    val CEO_Telephone: String,

    @JsonProperty("crtp")
    val Certification_Phone: Boolean,
    @JsonProperty("crte")
    val Certification_Email: Boolean,

    @JsonProperty("pcst")
    val Prior_Consent: String?,

    @JsonProperty("agtm")
    val Agree_Terms: AgreeType,

    @JsonProperty("bnam")
    val Bank_Name: String?,
    @JsonProperty("bacc")
    val Bank_Account: String?,
    @JsonProperty("bhld")
    val Bank_Holder: String?,

    @JsonProperty("admm")
    val Admin_Memo: String?,

    @JsonProperty("jpro")
    val Join_Progress: ContactProgressType,

    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime,
): BaseDTO(Modify_Datetime, Register_Datetime)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellPartnerDTOSignup (
    @JsonProperty("idx")
    val Idx: UUID,

    @JsonProperty("ceon")
    val CEO_Name: String,
    @JsonProperty("ceot")
    val CEO_Telephone: String,


    @JsonProperty("txnm")
    val Tax_Number: String,

    @JsonProperty("txem")
    val Tax_Email: String,

    @JsonProperty("txrfn")
    val Tax_Registration_DocumentFileName: String,
    @JsonProperty("txrd")
    val Tax_Registration_DocumentFile: UUID,
    @JsonProperty("ceoin")
    val CEO_IDCard_FileName: String,
    @JsonProperty("ceoi")
    val CEO_IDCard_File: UUID,

    @JsonProperty("cttp")
    val Contact_Type: ContactType,
    @JsonProperty("agtm")
    val Agree_Terms: AgreeType,

    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime,
): BaseDTO(Modify_Datetime, Register_Datetime)