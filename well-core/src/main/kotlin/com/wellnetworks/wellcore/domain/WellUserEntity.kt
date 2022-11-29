package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import java.util.*
import java.time.ZonedDateTime
import javax.persistence.*
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.PermissionList
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_tb", indexes =
  [Index(name = "IX_uid", columnList = "uid ASC", unique = true)]
)
data class WellUserEntity(
    @Id
    @Column(name = "idx", unique = true, nullable = false)
    var Idx: UUID,

    @Column(name = "uid", length = 32, unique = true, nullable = false)
    var UserID: String,

    @Column(name = "permissions")
    @Convert(converter = ListToStringConverter::class)
    var PermissionsKeysStringList: List<String>,

    @Column(name = "pwd", length = 255, nullable = false)
    var PasswordHash: String,

    @Column(name = "tmp_pwd", length = 255, nullable = false)
    var TemporaryPassword: String,

    @Column(name = "tmp_pwd_exp")
    var TemporaryPasswordExpire: ZonedDateTime,

    @Column(name = "tmp_pwd_cnt")
    var TemporaryPasswordCreateCount: Byte = 0,

    @Column(name = "tmp_pwd_dt")
    var TemporaryPasswordCreateDatetime: ZonedDateTime,
): BaseEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        // Todo : 임시 패스워드 발급 상태인지 퍼미션 확인 후 조건에 맞게 처리.
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
        // Todo : PERMISSION_LOGIN 유무를 판단.
        return true
    }

    fun getWellUserDTO(): WellUserDTO {
        return WellUserDTO(
            Idx = this.Idx,
            UserID = this.UserID,
            PermissionsKeysStringList = this.PermissionsKeysStringList,
            Password_Hash = this.PasswordHash,
            Temporary_Password = this.TemporaryPassword,
            Temporary_Password_Expire = this.TemporaryPasswordExpire,
            Temporary_Password_Create_Count = this.TemporaryPasswordCreateCount,
            Temporary_Password_Create_Datetime = this.TemporaryPasswordCreateDatetime,
            Modify_Datetime = this.ModifyDatetime,
            Register_Datetime = this.RegisterDatetime,
        )
    }

    fun createWellUserDTO(): WellUserDTO_Create {
        return WellUserDTO_Create(
            UserID = this.UserID,
            PermissionsKeysStringList = this.PermissionsKeysStringList,
            Password_Hash = this.PasswordHash,
            Modify_Datetime = this.ModifyDatetime,
            Register_Datetime = this.RegisterDatetime,
        )
    }

    fun checkPrmisstion(wellPermission: String): Boolean {
        return PermissionsKeysStringList.contains(wellPermission)
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellUserEntity

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