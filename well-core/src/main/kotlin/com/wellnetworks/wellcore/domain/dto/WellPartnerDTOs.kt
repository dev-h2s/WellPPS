package com.wellnetworks.wellcore.domain.dto

import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.UUID

data class WellPartnerDTO (
    val Idx: UUID,
    val UserIdx: UUID,
    val TableID: String,

    val Company_Name: String,
    val Company_Type: CompanyType,
    val Company_Group: Byte,
    val Company_State: CompanyStateType,
    val Company_Level: Byte,
    val Company_Address1: String,
    val Company_Address2: String,

    val Tax_Number: String,
    val Tax_Email: String,
    val Tax_Address1: String,
    val Tax_Address2: String,

    val Office_Telephone: String,
    val Office_Email: String,
    val Rate: RateType,

    val Contact_Person : String,
    val Contact_Type: ContactType,
    val Contact_Phone: String,
    val Contact_Datetime: ZonedDateTime,
    val Contact_Address1: String,
    val Contact_Address2: String,
    val Contact_Progress: ContactProgressType,
    val Contact_Reject: ContactRejectType,
    val Contact_Approver: String,
    val Contact_Register_Datetime: ZonedDateTime,
    val Contact_Modify_Datetime: ZonedDateTime,
    val Use_API: Boolean,

    val Organization_Parent: UUID,
    val Organization_Child: UUID,

    val CEO_Name: String,
    val CEO_Telephone: String,

    val Certification_Phone: Boolean,
    val Certification_Email: Boolean,

    val Prior_Consent: String,

    val Agree_Terms: AgreeType,
    val Agree_Terms_Datetime: ZonedDateTime,
    val Agree_Terms_Modify_Datetime: ZonedDateTime,

    val Bank_Name: String,
    val Bank_Account: String,
    val Bank_Holder: String,

    val Admin_Memo: String,

    val Join_Progress: ContactProgressType,

    override val Modify_Datetime: ZonedDateTime,
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
