package com.wellnetworks.wellcore.domain

import java.util.*
import java.time.ZonedDateTime
import javax.persistence.*
import com.wellnetworks.wellcore.domain.dto.usersDTO
import org.hibernate.Hibernate

@Entity
@Table(name = "user_tb")
data class Users(
    @Id
    @Column(name = "idx", length = 16, unique = true, nullable = false)
    var idx: UUID? = UUID.randomUUID(),

    @Column(name = "uid", length = 32, unique = true, nullable = false)
    var userid: String,

    @Column(name = "rule", nullable = false)
    @Convert(converter = RuleConverter::class)
    var rule: RuleTypes = RuleTypes.None,

    @Column(name = "passwd")
    var passwordhash: String,

    @Column(name = "passwd_temp")
    var password_temporary: String,

    @Column(name = "passwd_expire")
    var password_expire: ZonedDateTime,

    @Column(name = "passwd_ct")
    var password_ct: Byte = 0,

    @Column(name = "passwd_cert")
    var password_certification: Boolean,

    @Column(name = "passwd_dt")
    var create_temporary_password_datetime: ZonedDateTime,
): baseEntity() {
    fun getUsersDTO(): usersDTO {
        return usersDTO(
            idx = this.idx,
            userid = this.userid,
            rule = this.rule,
            passwordhash = this.passwordhash,
            password_temporary = this.password_temporary,
            password_expire = this.password_expire,
            password_ct = this.password_ct,
            password_certification = this.password_certification,
            create_temporary_password_datetime = this.create_temporary_password_datetime
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Users

        return idx != null && idx == other.idx
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this.userid
    }
}

enum class RuleTypes(val rule: String) {
    None("none"),
    Member("member"),
    Partner("partner"),
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