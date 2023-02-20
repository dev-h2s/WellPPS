package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.Hibernate
import java.time.ZonedDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WellGroupDTO (
    @JsonProperty("idx")
    val Idx: String,

    @JsonProperty("label")
    val Label: String,

    @JsonProperty("pkey")
    val PermissionKeysStringList: List<String>,

    @JsonProperty("desc")
    val Description: String?,

    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
) : BaseDTO(Modify_Datetime, Register_Datetime) {
    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellUserDTO

        return Idx != null && Idx.uppercase() == other.Idx.uppercase();
    }
}