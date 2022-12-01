package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import java.util.*
import java.time.ZonedDateTime
import javax.persistence.*
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_tb", indexes =
  [Index(name = "IX_uid", columnList = "uid ASC", unique = true)]
)
data class WellUserEntity(
    @Id
    @Column(name = "idx", unique = true, nullable = false)
    var idx: UUID,

    @Column(name = "uid", length = 32, unique = true, nullable = false)
    var userID: String,

    @Column(name = "permissions")
    @Convert(converter = ListToStringConverter::class)
    var permissionsKeysStringList: List<String>,

    @Column(name = "pwd", length = 255, nullable = false)
    var passwordHash: String,

    @Column(name = "tmp_pwd", length = 255, nullable = false)
    var temporaryPassword: String,

    @Column(name = "tmp_pwd_exp")
    var temporaryPasswordExpire: ZonedDateTime,

    @Column(name = "tmp_pwd_cnt")
    var temporaryPasswordCreateCount: Byte = 0,

    @Column(name = "tmp_pwd_dt")
    var temporaryPasswordCreateDatetime: ZonedDateTime,
): BaseEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        var authorities: MutableList<GrantedAuthority> = ArrayList()
        for (permission in permissionsKeysStringList) {
            authorities.add(SimpleGrantedAuthority(permission))
        }
        return authorities
    }

    override fun getPassword(): String {
        // Todo : 임시 패스워드 발급 상태인지 퍼미션 확인 후 조건에 맞게 처리.
        return passwordHash
    }

    override fun getUsername(): String {
        return userID
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
        // Todo : PERMISSION_LOGIN 유무를 판단.
        return true
    }

    fun getWellUserDTO(): WellUserDTO {
        return WellUserDTO(
            Idx = this.idx,
            UserID = this.userID,
            PermissionsKeysStringList = this.permissionsKeysStringList,
            Password_Hash = this.passwordHash,
            Temporary_Password = this.temporaryPassword,
            Temporary_Password_Expire = this.temporaryPasswordExpire,
            Temporary_Password_Create_Count = this.temporaryPasswordCreateCount,
            Temporary_Password_Create_Datetime = this.temporaryPasswordCreateDatetime,
            Modify_Datetime = this.modifyDatetime,
            Register_Datetime = this.registerDatetime,
        )
    }

    fun checkPrmisstion(wellPermission: String): Boolean {
        return permissionsKeysStringList.contains(wellPermission)
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellUserEntity

        return idx != null && idx == other.idx;
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    @Override
    override fun toString(): String {
        return this.userID
    }
}