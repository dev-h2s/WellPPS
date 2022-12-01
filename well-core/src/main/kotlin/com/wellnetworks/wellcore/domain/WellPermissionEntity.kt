package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.dto.WellPermissionDTO
import org.hibernate.Hibernate
import javax.persistence.*;

@Entity
@Table(name = "permission_tb")
class WellPermissionEntity (
    @Id
    @Column(name = "pkey", length = 64, unique = true, nullable = false)
    var permissionKey : String,

    @Column(name = "name", length = 32, nullable = false)
    var keyName : String,

    @Column(name = "desc")
    var description : String,
): BaseEntity() {


    fun getWellPermisionDTO(): WellPermissionDTO {
        return WellPermissionDTO(
            Key = this.permissionKey,
            Name = this.keyName,
            Description = this.description,
            Modify_Datetime = this.modifyDatetime,
            Register_Datetime = this.registerDatetime,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as WellPermissionEntity

        return permissionKey != null && permissionKey == other.permissionKey;
    }

    override fun hashCode(): Int {
        return permissionKey.hashCode();
    }

    override fun toString(): String {
        return this.keyName;
    }
}