package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.UUID

data class WellFileStorageDTO (
    var Idx: UUID,
    var TableID: String,
    var Writer: UUID,
    var Create_YYYYMM: String,
    var FileName: String,
    var FileDescription: String?,
    var isLinkError: Boolean,
    var isPublic: Boolean,
    val PermissionsKeysStringList: List<String>?,
    var FileSize: Long,
    var FileExtension: String?,
    var FileDownloadCount: Long,
    override val Modify_Datetime: ZonedDateTime?,
    override val Register_Datetime: ZonedDateTime?,
): BaseDTO(Modify_Datetime, Register_Datetime) {
    override fun hashCode(): Int {
        return Idx.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellFileStorageDTO

        return Idx != null && Idx == other.Idx;
    }
}