package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
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
    Contract_DocFileIdx("contractDocFile"),
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

//@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellPartnerDTO (
    @JsonProperty("idx")
    val Idx: String,
    @JsonProperty("p")
    val P_Code: String?,
    @JsonProperty("tid")
    val TableID: String,

    @JsonProperty("cnam")
    val Company_Name: String?,
    @JsonProperty("ctyp")
    val Company_Type: CompanyType,
    @JsonProperty("ctyp_str")
    val Company_Type_String: String?,
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
    val Tax_RegistrationDocumentFileIdx: String?,
    @JsonProperty("txnm")
    val Tax_Number: String?,
    @JsonProperty("txem")
    val Tax_Email: String?,
    @JsonProperty("txa1")
    val Tax_Address1: String?,
    @JsonProperty("txa2")
    val Tax_Address2: String?,

    @JsonProperty("confn")
    val Contract_DocumentFileName: String?,
    @JsonProperty("conrd")
    val Contract_DocFileIdx: String?,

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
    val Organization_Parent: String?,
    @JsonProperty("orgc")
    val Organization_Child: String?,

    @JsonProperty("ceoin")
    val CEO_IDCard_FileName: String?,
    @JsonProperty("ceoi")
    val CEO_IDCard_FileIdx: String?,
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
    @JsonProperty("agtdt")
    val Agree_Terms_Datetime: ZonedDateTime,
    @JsonProperty("agtmdt")
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
    override var Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
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
    val Tax_Registration_DocumentFileName: String?,
//    @JsonProperty("txrd")
//    val Tax_Registration_DocumentFile: String?,
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
    val Organization_Parent: String?,
    @JsonProperty("orgc")
    val Organization_Child: String?,

    @JsonProperty("ceoin")
    val CEO_IDCard_FileName: String?,
//    @JsonProperty("ceoi")
//    val CEO_IDCard_File: String?,
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
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
): BaseDTO(Modify_Datetime, Register_Datetime)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellPartnerDTOSignup (
    //@JsonProperty("idx")
    //val Idx: UUID?=null,

    @JsonProperty("ceo_name")
    val CEO_Name: String,
    @JsonProperty("ceo_tel")
    val CEO_Telephone: String,
    @JsonProperty("tax_number")
    val Tax_Number: String,
    @JsonProperty("tax_email")
    val Tax_Email: String,
    @JsonProperty("tax_file_name")
    val Tax_Registration_DocumentFileName: String?,
    //@JsonProperty("txrd")
    //val Tax_Registration_DocumentFile: UUID,
    @JsonProperty("idcard_file_name")
    val CEO_IDCard_FileName: String?,
    //@JsonProperty("ceoi")
    //val CEO_IDCard_File: UUID,
    @JsonProperty("con_type")
    val Contact_Type: ContactType,
    @JsonProperty("con_progress")
    val Join_Progress: ContactProgressType,
    @JsonProperty("agree_type")
    val Agree_Terms: AgreeType,
    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
): BaseDTO(Modify_Datetime, Register_Datetime)

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class WellPartnerDTOUpdate @JsonCreator constructor (
    @JsonProperty("idx")
    val Idx: String?,
    @JsonProperty("c_name")
    val Company_Name: String?,
    @JsonProperty("c_type")
    val Company_Type: CompanyType?,
    @JsonProperty("c_group")
    val Company_Group: Byte?,
    @JsonProperty("c_rate")
    val Rate: RateType?,
    @JsonProperty("contact_person")
    val Contact_Person : String?,
    @JsonProperty("use_api")
    val Use_API: Boolean,
    @JsonProperty("contact_datetime")
    val Contact_Datetime: ZonedDateTime?,
    @JsonProperty("c_state")
    val Company_State: CompanyStateType?,
    @JsonProperty("c_level")
    val Company_Level: Byte?,
    @JsonProperty("parent_idx")
    val Organization_Parent: String?,
    @JsonProperty("child_idx")
    val Organization_Child: String?,
    @JsonProperty("ceo_name")
    val CEO_Name: String?,
    @JsonProperty("ceo_tel")
    val CEO_Telephone: String?,
    @JsonProperty("office_tel")
    val Office_Telephone: String?,
    @JsonProperty("office_email")
    val Office_Email: String?,
    @JsonProperty("phone_cert")
    val Certification_Phone: Boolean?,
    @JsonProperty("email_cert")
    val Certification_Email: Boolean?,
    @JsonProperty("bank_name")
    val Bank_Name: String?,
    @JsonProperty("bank_account")
    val Bank_Account: String?,
    @JsonProperty("bank_holder")
    val Bank_Holder: String?,
    @JsonProperty("tax_number")
    val Tax_Number: String?,
    @JsonProperty("tax_email")
    val Tax_Email: String?,
    @JsonProperty("Prior_approval_number")
    val Prior_Consent: String?,
    @JsonProperty("tax_addr1")
    val Tax_Address1: String?,
    @JsonProperty("tax_addr2")
    val Tax_Address2: String?,
    @JsonProperty("com_addr1")
    val Company_Address1: String?,
    @JsonProperty("com_addr2")
    val Company_Address2: String?,
    @JsonProperty("agree_type")
    val Agree_Terms: AgreeType?,
    @JsonProperty("agree_date")
    val Agree_Terms_Datetime: ZonedDateTime?,
    @JsonProperty("agree_mod_date")
    val Agree_Terms_Modify_Datetime: ZonedDateTime?,
    @JsonProperty("tax_file_idx")
    val Tax_RegistrationDocumentFileIdx: String?,
    @JsonProperty("contract_file_idx")
    val Contract_DocFileIdx: String?,
    @JsonProperty("idcard_file_idx")
    val CEO_IDCard_FileIdx: String?,
    @JsonProperty("tax_file_name")
    val Tax_Registration_DocumentFileName: String?,
    @JsonProperty("contract_file_name")
    val Contract_DocumentFileName: String?,
    @JsonProperty("idcard_file_name")
    val CEO_IDCard_FileName: String?,
    @JsonProperty("memo")
    val Admin_Memo: String?,

    @JsonIgnore
    override var Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
): BaseDTO(Modify_Datetime, Register_Datetime)