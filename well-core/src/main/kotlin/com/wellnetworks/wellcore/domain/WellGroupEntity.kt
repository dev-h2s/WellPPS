package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellGroupDTO
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import jakarta.persistence.*

@Entity
@Table(name = "user_group_list_tb")
data class WellGroupEntity(
    @Id
    @Column(name = "gkey", length = 32, nullable = false)
    var groupPermissionKey: String,

    @Column(name = "label", length = 32, nullable = false)
    var label: String,

    @Column(name = "permissions", nullable = true)
    @Convert(converter = ListToStringConverter::class)
    var groupPermissionsKeysStringList: List<String>,

    @Column(name = "description", length = 255, nullable = true)
    var description: String?,
): BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellGroupEntity

        return groupPermissionKey != null && groupPermissionKey.uppercase() == other.groupPermissionKey.uppercase();
    }

    override fun hashCode(): Int {
        return groupPermissionKey.hashCode();
    }

    override fun toString(): String {
        return label ?: ""
    }

    fun toDto(): WellGroupDTO = WellGroupDTO(
        GroupKey = this.groupPermissionKey.uppercase(),
        Label = this.label,
        GroupPermissionKeysStringList = this.groupPermissionsKeysStringList,
        Description = this.description,
        Modify_Datetime = this.modifyDatetime,
        Register_Datetime = this.registerDatetime,
    )

    fun updateDto(dto: WellGroupDTO) {
        this.label = dto.Label
        this.groupPermissionsKeysStringList = dto.GroupPermissionKeysStringList
        this.description = dto.Description
        this.modifyDatetime = ZonedDateTime.now()
    }
}


