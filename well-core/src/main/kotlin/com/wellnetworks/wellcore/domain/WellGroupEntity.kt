package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellGroupDTO
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "user_group_list_tb", indexes =
  [Index(name = "IX_uid", columnList = "uid ASC", unique = true)]
)
data class WellGroupEntity(
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    @Column(name = "label", length = 32, nullable = false)
    var Label: String,

    @Column(name = "permissions", nullable = true)
    @Convert(converter = ListToStringConverter::class)
    var permissionsKeysStringList: List<String>,

    @Column(name = "description", length = 255, nullable = true)
    var description: String?,
): BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerEntity

        return idx != null && idx.uppercase() == other.idx.uppercase();
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    override fun toString(): String {
        return Label ?: ""
    }

    fun toDto(): WellGroupDTO = WellGroupDTO(
        Idx = this.idx.uppercase(),
        Label = this.Label,
        PermissionKeysStringList = this.permissionsKeysStringList,
        Description = this.description,
        Modify_Datetime = this.modifyDatetime,
        Register_Datetime = this.registerDatetime,
    )

    fun updateDto(dto: WellGroupDTO) {
        this.Label = dto.Label
        this.permissionsKeysStringList = dto.PermissionKeysStringList
        this.description = dto.Description
        this.modifyDatetime = ZonedDateTime.now()
    }
}


