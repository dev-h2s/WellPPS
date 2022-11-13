package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellUserCreateDTO
import java.util.*
import java.time.ZonedDateTime
import javax.persistence.*
import com.wellnetworks.wellcore.domain.dto.WellUserDTO
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_tb", indexes =
  [Index(name = "IX_usr", columnList = "uid ASC", unique = true)]
)
data class WellUser(
    @Id
    @Column(name = "idx", unique = true, nullable = false)
    var idx: UUID? = UUID.randomUUID(),

    @Column(name = "uid", length = 32, unique = true, nullable = false)
    var userid: String,

    @Column(name = "rule", nullable = false)
    @Convert(converter = RuleConverter::class)
    var rule: RuleTypes = RuleTypes.NONE,

    @Column(name = "permission", nullable = true)
    @Convert(converter = ListToStringConverter::class)
    var permission: List<String>,

    @Column(name = "passwd", length = 128)
    var password_hash: String,

    @Column(name = "passwd_temp", length = 128)
    var password_temporary: String,

    @Column(name = "passwd_expire")
    var password_expire: ZonedDateTime,

    @Column(name = "passwd_ct")
    var password_ct: Byte = 0,

    @Column(name = "passwd_cert")
    var password_certification: Boolean,

    @Column(name = "passwd_dt")
    var create_temporary_password_datetime: ZonedDateTime,
): BaseEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return password_hash
    }

    override fun getUsername(): String {
        return userid
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
            idx = this.idx,
            userid = this.userid,
            rule = this.rule,
            permission = this.permission,
            password_hash = this.password_hash,
            password_temporary = this.password_temporary,
            password_expire = this.password_expire,
            password_ct = this.password_ct,
            password_certification = this.password_certification,
            create_temporary_password_datetime = this.create_temporary_password_datetime,
            modify_datetime = this.modify_datetime,
            register_datetime = this.register_datetime,
        )
    }

    fun createWellUserDTO(): WellUserCreateDTO {
        return WellUserCreateDTO(
            userid = this.userid,
            rule = this.rule,
            permission = this.permission,
            password_hash = this.password_hash,
            modify_datetime = this.modify_datetime,
            register_datetime = this.register_datetime,
        )
    }

    fun checkPrmisstion(wellPermission: WellPermission): Boolean {
        return permission.contains(wellPermission.name)
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as WellUser

        return idx != null && idx == other.idx
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this.userid
    }
}

enum class RuleTypes(val rule: String) {
    NONE("none"),
    MEMBER("member"),
    PARTNER("partner"),
    ;
}

@Converter
class RuleConverter : AttributeConverter<RuleTypes, String> {
    override fun convertToDatabaseColumn(RuleType: RuleTypes): String {
        return RuleType.rule
    }

    override fun convertToEntityAttribute(value: String): RuleTypes {
        return RuleTypes.valueOf(value.uppercase())
    }
}