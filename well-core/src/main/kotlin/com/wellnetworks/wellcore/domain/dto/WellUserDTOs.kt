package com.wellnetworks.wellcore.domain.dto

import com.wellnetworks.wellcore.domain.WellUserEntity
import java.time.ZonedDateTime
import java.util.*
import com.wellnetworks.wellcore.domain.dto.BaseDTO
import org.hibernate.Hibernate

data class WellUserDTO (
    val Idx: UUID,
    val UserID: String,
    val PermissionsKeysStringList: List<String>,
    val Password_Hash: String,
    val Temporary_Password: String,
    val Temporary_Password_Expire: ZonedDateTime,
    val Temporary_Password_Create_Count: Byte = 0,
    val Temporary_Password_Create_Datetime: ZonedDateTime,
    override val Modify_Datetime: ZonedDateTime,
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

data class WellUserDTO_Create (
    val Idx: UUID? = UUID.randomUUID(),
    val UserID: String,
    val PermissionsKeysStringList: List<String>,
    val Password_Hash: String,
    override val Modify_Datetime: ZonedDateTime,
    override val Register_Datetime: ZonedDateTime,
): BaseDTO(Modify_Datetime, Register_Datetime) {

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellUserDTO_Create

        return Idx != null && Idx == other.Idx;
    }
}