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
    @JsonProperty("pcode")
    val P_Code: String?,
    @JsonProperty("table_id")
    val TableID: String,

    @JsonProperty("c_name")
    val Company_Name: String?,
    @JsonProperty("c_type")
    val Company_Type: CompanyType,
    @JsonProperty("c_type_string")
    val Company_Type_String: String?,
    @JsonProperty("c_group")
    val Company_Group: Byte?,
    @JsonProperty("c_state")
    val Company_State: CompanyStateType,
    @JsonProperty("c_level")
    val Company_Level: Byte?,
    @JsonProperty("c_addr1")
    val Company_Address1: String?,
    @JsonProperty("c_addr2")
    val Company_Address2: String?,

    @JsonProperty("tax_file_name")
    val Tax_Registration_DocumentFileName: String?,
    @JsonProperty("tax_file_idx")
    val Tax_RegistrationDocumentFileIdx: String?,
    @JsonProperty("tax_number")
    val Tax_Number: String?,
    @JsonProperty("tax_email")
    val Tax_Email: String?,
    @JsonProperty("t_addr1")
    val Tax_Address1: String?,
    @JsonProperty("t_addr2")
    val Tax_Address2: String?,

    @JsonProperty("contract_file_name")
    val Contract_DocumentFileName: String?,
    @JsonProperty("contract_file_idx")
    val Contract_DocFileIdx: String?,

    @JsonProperty("office_tel")
    val Office_Telephone: String?,
    @JsonProperty("office_email")
    val Office_Email: String?,
    @JsonProperty("c_rate")
    val Rate: RateType,

    @JsonProperty("con_person")
    val Contact_Person : String?,
    @JsonProperty("con_type")
    val Contact_Type: ContactType,
    @JsonProperty("con_phone")
    val Contact_Phone: String?,
    @JsonProperty("con_dt")
    val Contact_Datetime: ZonedDateTime?,
    @JsonProperty("con_addr1")
    val Contact_Address1: String?,
    @JsonProperty("con_addr2")
    val Contact_Address2: String?,
    @JsonProperty("con_progress")
    val Contact_Progress: ContactProgressType, //방문요청 진행도
    @JsonProperty("con_reject")
    val Contact_Reject: ContactRejectType,
    @JsonProperty("con_approver") //검수자
    val Contact_Approver: String?,
    @JsonProperty("con_reg_dt")
    val Contact_Register_Datetime: ZonedDateTime?,
    @JsonProperty("con_mod_dt")
    val Contact_Modify_Datetime: ZonedDateTime?,
    @JsonProperty("use_api")
    val Use_API: Boolean,

    @JsonProperty("parent_idx")
    val Organization_Parent: String?,
    @JsonProperty("child_idx")
    val Organization_Child: String?,

    @JsonProperty("idcard_file_name")
    val CEO_IDCard_FileName: String?,
    @JsonProperty("idcard_file_idx")
    val CEO_IDCard_FileIdx: String?,
    @JsonProperty("ceo_name")
    val CEO_Name: String,
    @JsonProperty("ceo_tel")
    val CEO_Telephone: String,

    @JsonProperty("phone_cert")
    val Certification_Phone: Boolean,
    @JsonProperty("email_cert")
    val Certification_Email: Boolean,

    @JsonProperty("prior_approval_number")
    val Prior_Consent: String?,

    @JsonProperty("agree_type")
    val Agree_Terms: AgreeType,
    @JsonProperty("agree_reg_dt")
    val Agree_Terms_Datetime: ZonedDateTime,
    @JsonProperty("agree_mod_dt")
    val Agree_Terms_Modify_Datetime: ZonedDateTime?,

    @JsonProperty("bank_name")
    val Bank_Name: String?,
    @JsonProperty("bank_account")
    val Bank_Account: String?,
    @JsonProperty("bank_holder")
    val Bank_Holder: String?,

    @JsonProperty("memo")
    val Admin_Memo: String?,

    @JsonProperty("join_progress")
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
    @JsonProperty("table_id")
    val TableID: String?,
    @JsonProperty("pcode")
    val P_Code: String,

    @JsonProperty("c_name")
    val Company_Name: String,
    @JsonProperty("c_type")
    val Company_Type: CompanyType,
    @JsonProperty("c_group")
    val Company_Group: Byte?,
    @JsonProperty("c_state")
    val Company_State: CompanyStateType,
    @JsonProperty("c_level")
    val Company_Level: Byte?,
    @JsonProperty("c_addr1")
    val Company_Address1: String?,
    @JsonProperty("c_addr2")
    val Company_Address2: String?,

    @JsonProperty("tax_file_name")
    val Tax_Registration_DocumentFileName: String?,
//    @JsonProperty("tax_file_idx")
//    val Tax_Registration_DocumentFile: String?,
    @JsonProperty("tax_number")
    val Tax_Number: String?,
    @JsonProperty("tax_email")
    val Tax_Email: String?,
    @JsonProperty("t_addr1")
    val Tax_Address1: String?,
    @JsonProperty("t_addr2")
    val Tax_Address2: String?,

    @JsonProperty("office_tel")
    val Office_Telephone: String?,
    @JsonProperty("office_email")
    val Office_Email: String?,
    @JsonProperty("rate")
    val Rate: RateType,

    @JsonProperty("con_person")
    val Contact_Person : String?,
    @JsonProperty("con_type")
    val Contact_Type: ContactType,
    @JsonProperty("con_phone")
    val Contact_Phone: String?,
    @JsonProperty("con_dt")
    val Contact_Datetime: ZonedDateTime?,
    @JsonProperty("con_addr1")
    val Contact_Address1: String?,
    @JsonProperty("con_addr2")
    val Contact_Address2: String?,
    @JsonProperty("con_progress")
    val Contact_Progress: ContactProgressType,
    @JsonProperty("con_reject")
    val Contact_Reject: ContactRejectType,
    @JsonProperty("con_approver")
    val Contact_Approver: String?,
    @JsonProperty("con_reg_dt")
    val Contact_Register_Datetime: ZonedDateTime?,
    @JsonProperty("con_mod_dt")
    val Contact_Modify_Datetime: ZonedDateTime?,
    @JsonProperty("use_api")
    val Use_API: Boolean,

    @JsonProperty("parent_idx")
    val Organization_Parent: String?,
    @JsonProperty("child_idx")
    val Organization_Child: String?,

    @JsonProperty("idcard_file_name")
    val CEO_IDCard_FileName: String?,
//    @JsonProperty("idcard_file_idx")
//    val CEO_IDCard_File: String?,
    @JsonProperty("ceo_name")
    val CEO_Name: String,
    @JsonProperty("ceo_tel")
    val CEO_Telephone: String,

    @JsonProperty("phone_cert")
    val Certification_Phone: Boolean,
    @JsonProperty("email_cert")
    val Certification_Email: Boolean,

    @JsonProperty("prior_approval_number")
    val Prior_Consent: String?,

    @JsonProperty("agree_type")
    val Agree_Terms: AgreeType,

    @JsonProperty("bank_name")
    val Bank_Name: String?,
    @JsonProperty("bank_account")
    val Bank_Account: String?,
    @JsonProperty("bank_holder")
    val Bank_Holder: String?,

    @JsonProperty("memo")
    val Admin_Memo: String?,

    @JsonProperty("join_progress")
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
    @JsonProperty("join_progress")
    val Join_Progress: ContactProgressType, //회원가입진행도
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
    @JsonProperty("prior_approval_number")
    val Prior_Consent: String?,
    @JsonProperty("t_addr1")
    val Tax_Address1: String?,
    @JsonProperty("t_addr2")
    val Tax_Address2: String?,
    @JsonProperty("c_addr1")
    val Company_Address1: String?,
    @JsonProperty("c_addr2")
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