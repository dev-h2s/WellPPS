package com.wellnetworks.wellcore.domain

import org.hibernate.Hibernate
import javax.persistence.*;

@Entity
@Table(name = "permission_tb")
class WellPermissionEntity (
    @Id
    @Column(name = "pkey", length = 64, unique = true, nullable = false)
    var PermissionKey : String,

    @Column(name = "name", length = 32, nullable = false)
    var KeyName : String,

    @Column(name = "desc")
    var Description : String,
): BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as WellPermissionEntity

        return PermissionKey != null && PermissionKey == other.PermissionKey;
    }

    override fun hashCode(): Int {
        return PermissionKey.hashCode();
    }

    override fun toString(): String {
        return this.KeyName;
    }
}