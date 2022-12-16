package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.wellnetworks.wellcore.domain.WellUserEntity
import java.time.ZonedDateTime
import java.util.*
import com.wellnetworks.wellcore.domain.dto.BaseDTO
import org.hibernate.Hibernate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellUserDTO (
    @JsonProperty("idx")
    val Idx: UUID,
    @JsonProperty("uid")
    val UserID: String,
    @JsonProperty("pkey")
    val PermissionsKeysStringList: List<String>,
    @JsonProperty("pwdh")
    val Password_Hash: String?,
    @JsonProperty("tpwd")
    val Temporary_Password: String?,
    @JsonProperty("tpwx")
    val Temporary_Password_Expire: ZonedDateTime?,
    @JsonProperty("tpwc")
    val Temporary_Password_Create_Count: Byte? = 0,
    @JsonProperty("tpwt")
    val Temporary_Password_Create_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime,
): BaseDTO(Modify_Datetime, Register_Datetime) {

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellUserDTO

        return Idx != null && Idx == other.Idx;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellUserDTOCreate(
    @JsonProperty("uid")
    val UserID: String,
    @JsonProperty("pkey")
    val PermissionsKeysStringList: List<String>,
    @JsonProperty("pwd")
    val Password_Hash: String?,
    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime = ZonedDateTime.now(),
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime = ZonedDateTime.now(),
): BaseDTO(Modify_Datetime, Register_Datetime)