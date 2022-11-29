package com.wellnetworks.wellcore.domain.dto

import com.wellnetworks.wellcore.domain.WellUserEntity
import org.hibernate.Hibernate
import java.time.ZonedDateTime

data class WellPermissionDTO (
    val Key: String,
    val Name: String,
    val Description: String,
    override val Modify_Datetime: ZonedDateTime,
    override val Register_Datetime: ZonedDateTime,
): BaseDTO(Modify_Datetime, Register_Datetime) {

    override fun hashCode(): Int {
        return Key.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPermissionDTO

        return Key != null && Key == other.Key;
    }
}

