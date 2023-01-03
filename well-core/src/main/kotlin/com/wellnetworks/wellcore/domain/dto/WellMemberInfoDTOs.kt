package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellMemberInfoDTO(
    @JsonProperty("idx")
    val Idx: String,
    @JsonProperty("tid")
    val Table_ID: String,
    @JsonProperty("cure")
    val Current_Employment: CurrentEmploymentType,
    @JsonProperty("name")
    val Name: String,
    @JsonProperty("mail")
    val Email: String,
    @JsonProperty("pphn")
    val Phone_Private: String,
    @JsonProperty("wphn")
    val Phone_Work: String,
    @JsonProperty("regn")
    val Registration_Number: String,
    @JsonProperty("had1")
    val Home_Address1: String,
    @JsonProperty("had2")
    val Home_Address2: String,
    @JsonProperty("bnam")
    val Bank_Name: String,
    @JsonProperty("bacc")
    val Bank_Account: String,
    @JsonProperty("bhld")
    val Bank_Holder: String,
    @JsonProperty("dert")
    val Department: DepartmentType,
    @JsonProperty("jpos")
    val Job_Position: JobPositionType,
    @JsonProperty("lvl")
    val Level: Byte,
    @JsonProperty("emps")
    val Employment_State: EmploymentStateType,
    @JsonProperty("jtyp")
    val Job_Type: JobType,
    @JsonProperty("crtp")
    val Certification_Phone: Boolean,
    @JsonProperty("crte")
    val Certification_Email: Boolean,
    @JsonProperty("entd")
    val Entry_Datetime: ZonedDateTime,
    @JsonProperty("epqd")
    val Employment_Quit_Datetime: ZonedDateTime,
    @JsonProperty("epqt")
    val Employment_Quit_Type: EmploymentQuitType,
    @JsonProperty("accs")
    val Access: Boolean,
    @JsonProperty("memo")
    val Memo: String,
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
    val Table_ID: String,
    @JsonProperty("cure")
    val Current_Employment: CurrentEmploymentType,
    @JsonProperty("name")
    val Name: String,
    @JsonProperty("mail")
    val Email: String,
    @JsonProperty("pphn")
    val Phone_Private: String,
    @JsonProperty("wphn")
    val Phone_Work: String,
    @JsonProperty("regn")
    val Registration_Number: String,
    @JsonProperty("had1")
    val Home_Address1: String,
    @JsonProperty("had2")
    val Home_Address2: String,
    @JsonProperty("bnam")
    val Bank_Name: String,
    @JsonProperty("bacc")
    val Bank_Account: String,
    @JsonProperty("bhld")
    val Bank_Holder: String,
    @JsonProperty("dert")
    val Department: DepartmentType,
    @JsonProperty("jpos")
    val Job_Position: JobPositionType,
    @JsonProperty("lvl")
    val Level: Byte,
    @JsonProperty("emps")
    val Employment_State: EmploymentStateType,
    @JsonProperty("jtyp")
    val Job_Type: JobType,
    @JsonProperty("crtp")
    val Certification_Phone: Boolean,
    @JsonProperty("crte")
    val Certification_Email: Boolean,
    @JsonProperty("entd")
    val Entry_Datetime: ZonedDateTime,
    @JsonProperty("epqd")
    val Employment_Quit_Datetime: ZonedDateTime,
    @JsonProperty("epqt")
    val Employment_Quit_Type: EmploymentQuitType,
    @JsonProperty("accs")
    val Access: Boolean,
    @JsonProperty("memo")
    val Memo: String,
    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
): BaseDTO(Modify_Datetime, Register_Datetime)
