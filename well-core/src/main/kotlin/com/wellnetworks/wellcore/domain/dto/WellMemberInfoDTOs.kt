package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.wellnetworks.wellcore.domain.enums.*
import org.apache.coyote.Constants
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellMemberInfoDTO(
    @JsonProperty("idx")
    val Idx: String,
    @JsonProperty("table_id")
    val Table_ID: String,
    @JsonProperty("emp_current")
    val Current_Employment: CurrentEmploymentType,
    @JsonProperty("name")
    val Name: String,
    @JsonProperty("mail")
    val Email: String,
    @JsonProperty("phone_pri")
    val Phone_Private: String,
    @JsonProperty("phone_work")
    val Phone_Work: String,
    @JsonProperty("jumin")
    val Registration_Number: String,
    @JsonProperty("home_addr1")
    val Home_Address1: String,
    @JsonProperty("home_addr2")
    val Home_Address2: String,
    @JsonProperty("bank_name")
    val Bank_Name: String,
    @JsonProperty("bank_account")
    val Bank_Account: String,
    @JsonProperty("bank_holder")
    val Bank_Holder: String,
    @JsonProperty("dep")
    val Department: DepartmentType,
    @JsonProperty("pos")
    val Job_Position: JobPositionType,
    @JsonProperty("level")
    val Level: Byte,
    @JsonProperty("emp_state")
    val Employment_State: EmploymentStateType,
    @JsonProperty("emp_type")
    val Job_Type: JobType,
    @JsonProperty("phone_cert")
    val Certification_Phone: Boolean,
    @JsonProperty("email_cert")
    val Certification_Email: Boolean,
    @JsonProperty("entry_dt")
    val Entry_Datetime: ZonedDateTime,
    @JsonProperty("quit_dt")
    val Employment_Quit_Datetime: ZonedDateTime,
    @JsonProperty("quti_type")
    val Employment_Quit_Type: EmploymentQuitType,
    @JsonProperty("access")
    val Access: Boolean,

    @JsonProperty("file1")
    val Member_File1_idx: String,
    @JsonProperty("file2")
    val Member_File2_idx: String,
    @JsonProperty("file3")
    val Member_File3_idx: String,
    @JsonProperty("file4")
    val Member_File4_idx: String,
    @JsonProperty("file5")
    val Member_File5_idx: String,

    @JsonProperty("memo")
    val Memo: String?,

    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
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

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellMemberInfoDTOCreate(
    @JsonProperty("tid")
    val Table_ID: String?,
    @JsonProperty("emp_current")
    val Current_Employment: CurrentEmploymentType, //소속회사
    @JsonProperty("name")
    val Name: String,
    @JsonProperty("mail")
    val Email: String?,
    @JsonProperty("phone_pri")
    val Phone_Private: String?,
    @JsonProperty("phone_work")
    val Phone_Work: String?,
    @JsonProperty("jumin")
    val Registration_Number: String?,
    @JsonProperty("home_addr1")
    val Home_Address1: String?,
    @JsonProperty("home_addr2")
    val Home_Address2: String?,
    @JsonProperty("bank_name")
    val Bank_Name: String?,
    @JsonProperty("bank_account")
    val Bank_Account: String?,
    @JsonProperty("bank_holder")
    val Bank_Holder: String?,
    @JsonProperty("dep")
    val Department: DepartmentType, //부서
    @JsonProperty("pos")
    val Job_Position: JobPositionType, //직책
    @JsonProperty("level")
    val Level: Byte?,
    @JsonProperty("emp_status")
    val Employment_State: EmploymentStateType, //재직상태
    @JsonProperty("emp_type")
    val Job_Type: JobType, //고용형태
    @JsonProperty("phone_cert")
    val Certification_Phone: Boolean,
    @JsonProperty("phone_cert")
    val Certification_Email: Boolean,
    @JsonProperty("entry_dt")
    val Entry_Datetime: ZonedDateTime,
    @JsonProperty("quit_dt")
    val Employment_Quit_Datetime: ZonedDateTime,
    @JsonProperty("quti_type")
    val Employment_Quit_Type: EmploymentQuitType?,
    @JsonProperty("access")
    val Access: Boolean,

    @JsonProperty("file1")
    val Member_File1_idx: String?,
    @JsonProperty("file2")
    val Member_File2_idx: String?,
    @JsonProperty("file3")
    val Member_File3_idx: String?,
    @JsonProperty("file4")
    val Member_File4_idx: String?,
    @JsonProperty("file5")
    val Member_File5_idx: String?,

    @JsonProperty("memo")
    val Memo: String?,
    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
): BaseDTO(Modify_Datetime, Register_Datetime)
