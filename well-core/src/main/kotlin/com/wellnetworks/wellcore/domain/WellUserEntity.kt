package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import java.util.*
import java.time.ZonedDateTime
import javax.persistence.*
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import org.hibernate.annotations.ColumnDefault
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_tb", indexes =
  [Index(name = "IX_uid", columnList = "uid ASC", unique = true)]
)
data class WellUserEntity(
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    @Column(name = "uid", length = 32, unique = true, nullable = false)
    var userID: String,

    @Column(name = "permissions", nullable = true)
    @Convert(converter = ListToStringConverter::class)
    var permissionsKeysStringList: List<String>,

    @Column(name = "pwd", length = 255, nullable = true)
    var passwordHash: String?,

    @Column(name = "tmp_pwd", length = 255, nullable = false)
    var temporaryPassword: String,

    @Column(name = "tmp_pwd_exp", nullable = true)
    var temporaryPasswordExpire: ZonedDateTime?,

    @Column(name = "tmp_pwd_cnt")
    @ColumnDefault("0")
    var temporaryPasswordCreateCount: Byte = 0,

    @Column(name = "tmp_pwd_dt", nullable = true)
    var temporaryPasswordCreateDatetime: ZonedDateTime?,
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
        return passwordHash ?: ""
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
            Idx = this.idx.uppercase(),
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

        return idx != null && idx.uppercase() == other.idx.uppercase();
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    @Override
    override fun toString(): String {
        return this.userID
    }
/*
    fun updateDto (dto: WellUserDTOUpdate) {
        if (dto.UserID != null) this.userID = dto.UserID
        if (dto.PermissionsKeysStringList != null) this.permissionsKeysStringList = dto.PermissionsKeysStringList
        if (dto.Password_Hash != null) this.passwordHash = dto.Password_Hash
        if (dto.Temporary_Password != null) this.temporaryPassword = dto.Temporary_Password
        if (dto.Temporary_Password_Create_Count != null) this.temporaryPasswordCreateCount = dto.Temporary_Password_Create_Count
        if (dto.Temporary_Password_Expire != null) this.temporaryPasswordExpire = dto.Temporary_Password_Expire
        if (dto.Temporary_Password_Create_Datetime != null) this.temporaryPasswordCreateDatetime = dto.Temporary_Password_Create_Datetime


    }


 */
}