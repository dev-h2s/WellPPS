package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.wellnetworks.wellcore.domain.WellUserEntity
import org.hibernate.Hibernate
import java.time.ZonedDateTime
//권한 관련 DTO
@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellPermissionDTO (
    @JsonProperty("pkey")
    val Key: String,
    @JsonProperty("pname")
    val Name: String,
    @JsonProperty("pdesc")
    val Description: String?,
    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
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

