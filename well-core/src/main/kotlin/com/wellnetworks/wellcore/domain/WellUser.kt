package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellUserCreateDTO
import java.util.*
import java.time.ZonedDateTime
import javax.persistence.*
import com.wellnetworks.wellcore.domain.dto.WellUserDTO
import com.wellnetworks.wellcore.domain.enums.PermissionList
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_tb", indexes =
  [Index(name = "IX_uid", columnList = "uid ASC", unique = true)]
)
data class WellUser(
    @Id
    @Column(name = "idx", unique = true, nullable = false)
    var Idx: UUID? = UUID.randomUUID(),

    @Column(name = "uid", length = 32, unique = true, nullable = false)
    var UserID: String,

    @Column(name = "permissions")
    @Convert(converter = ListToStringConverter::class)
    var Permissions: List<String>,

    @Column(name = "pwd", length = 255, nullable = false)
    var PasswordHash: String,

    @Column(name = "tmp_pwd", length = 255, nullable = false)
    var TemporaryPassword: String,

    @Column(name = "tmp_pwd_exp")
    var TemporaryPasswordExpire: ZonedDateTime,

    @Column(name = "tmp_pwd_cnt")
    var TemporaryPasswordCreateCount: Byte = 0,

    @Column(name = "tmp_pwd_dt")
    var CreateTemporaryPasswordDatetime: ZonedDateTime,
): BaseEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return PasswordHash
    }

    override fun getUsername(): String {
        return UserID
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getWellUserDTO(): WellUserDTO {
        return WellUserDTO(
            idx = this.Idx,
            userid = this.UserID,
            permission = this.Permissions,
            password_hash = this.PasswordHash,
            password_temporary = this.TemporaryPassword,
            password_expire = this.TemporaryPasswordExpire,
            password_ct = this.TemporaryPasswordCreateCount,
            create_temporary_password_datetime = this.CreateTemporaryPasswordDatetime,
            modify_datetime = this.ModifyDatetime,
            register_datetime = this.RegisterDatetime,
        )
    }

    fun createWellUserDTO(): WellUserCreateDTO {
        return WellUserCreateDTO(
            userid = this.UserID,
            permission = this.Permissions,
            password_hash = this.PasswordHash,
            modify_datetime = this.ModifyDatetime,
            register_datetime = this.RegisterDatetime,
        )
    }

    fun checkPrmisstion(wellPermission: PermissionList): Boolean {
        return Permissions.contains(wellPermission.name)
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellUser

        return Idx != null && Idx == other.Idx;
    }

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    @Override
    override fun toString(): String {
        return this.UserID
    }
}

/*
@Converter
class RuleConverter : AttributeConverter<RuleTypes, String> {
    override fun convertToDatabaseColumn(RuleType: RuleTypes): String {
        return RuleType.rule
    }

    override fun convertToEntityAttribute(value: String): RuleTypes {
        return RuleTypes.valueOf(value.uppercase())
    }
}
*/