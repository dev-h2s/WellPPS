package com.wellnetworks.wellcore.domain.dto

import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.*

data class WellMemberInfoDTO(
    val Idx: UUID,
    val User_Idx: UUID,
    val Table_ID: String,
    val Current_Employment: CurrentEmploymentType,
    val Name: String,
    val Email: String,
    val Phone_Private: String,
    val Phone_Work: String,
    val Registration_Number: String,
    val Home_Address1: String,
    val Home_Address2: String,
    val Bank_Name: String,
    val Bank_Account: String,
    val Bank_Holder: String,
    val Department: DepartmentType,
    val Job_Position: JobPositionType,
    val Level: Byte,
    val Employment_State: EmploymentStateType,
    val Job_Type: JobType,
    val Certification_Phone: Boolean,
    val Certification_Email: Boolean,
    val Entry_Datetime: ZonedDateTime,
    val Employment_Quit_Datetime: ZonedDateTime,
    val Employment_Quit_Type: EmploymentQuitType,
    val Access: Boolean,
    val Memo: String,
    override val Modify_Datetime: ZonedDateTime,
    override val Register_Datetime: ZonedDateTime,
): BaseDTO(Modify_Datetime, Register_Datetime) {

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellMemberInfoDTO

        return Idx != null && Idx == other.Idx;
    }
}
