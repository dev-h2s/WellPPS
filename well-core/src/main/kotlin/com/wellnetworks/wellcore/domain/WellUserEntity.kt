package com.wellnetworks.wellcore.domain
// user 정보
import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import java.util.*
import java.time.ZonedDateTime
import jakarta.persistence.*
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
  [Index(name = "IX_uid", columnList = "uid ASC", unique = true),
   Index(name = "IX_gkey", columnList = "gkey ASC", unique = false)]
)
// user 정보 엔티티(db)
data class WellUserEntity(
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    @Column(name = "uid", length = 32, unique = true, nullable = false)
    var userID: String,

    @Column(name = "permissions", nullable = true)
    @Convert(converter = ListToStringConverter::class)
    var permissionsKeysStringList: List<String>,

    @Column(name = "gkey", length = 32, unique = false, nullable = true)
    var groupPermissionKey: String?,

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
    // UserDetails : 스프링 시큐리티에서 사용자의 상세 정보를 정의
    // BaseEntity() 클래스를 상속받아 사용
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        //사용자가 가지고 있는 권한 정보를 반환
        var authorities: MutableList<GrantedAuthority> = ArrayList()

        // Group Permission
        // 그룹 권한 추가
        if (!groupPermissionKey.isNullOrEmpty()) {
            authorities.add(SimpleGrantedAuthority(groupPermissionKey))
        }
        // 개별 권한 목록 추가
        for (permission in permissionsKeysStringList) {
            authorities.add(SimpleGrantedAuthority(permission))
        }

        return authorities
    }

    //user 비밀번호 반환 메소드
    override fun getPassword(): String {
        // Todo : 임시 패스워드 발급 상태인지 퍼미션 확인 후 조건에 맞게 처리.
        return passwordHash ?: ""
    }

    // user 이름 반환 메소드
    override fun getUsername(): String {
        return userID
    }

    //  user 계정(인증 정보) 만료 여부
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    // user 계정(인증 정보)의 잠금 상태(권한 예상)
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    // user 자격 증명 만료 여부(비밀번호 만료??)
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        // Todo : PERMISSION_LOGIN 유무를 판단.
        return true
    }

    //user dto 생성
    fun getWellUserDTO(): WellUserDTO {
        return WellUserDTO(
            Idx = this.idx.uppercase(),
            UserID = this.userID,
            GroupPermissionIdx = this.groupPermissionKey?.uppercase(),
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

    // 권한 체크
    fun checkPrmisstion(wellPermission: String): Boolean {
        return permissionsKeysStringList.contains(wellPermission)
    }

    // equals 메서드 재정의
    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        // this == other: 먼저, 비교하려는 객체가 같은 참조인지를 확인, 두 객체가 같은 참조라면 true를 반환하고 동일한 객체로 판단
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        // 비교하려는 객체 other가 null인지 또는 두 객체의 클래스가 다른지를 확인, 클래스가 다른 경우에는 두 객체를 다른 객체로 간주하고 false를 반환
        other as WellUserEntity

        return idx != null && idx.uppercase() == other.idx.uppercase();
    }

    // hashCode 재정의
    override fun hashCode(): Int {
        return idx.hashCode();
    }

    // 메서드 재정의
    @Override
    override fun toString(): String {
        return this.userID
    }

    // dto정보 업데이트
    fun updateDto (dto: WellUserDTOUpdate) {
        if (dto.UserID != null) this.userID = dto.UserID
        if (dto.PermissionsKeysStringList != null) this.permissionsKeysStringList = dto.PermissionsKeysStringList
        if (dto.Password_Hash != null) this.passwordHash = dto.Password_Hash
        if (dto.Temporary_Password != null) this.temporaryPassword = dto.Temporary_Password
        if (dto.Temporary_Password_Create_Count != null) this.temporaryPasswordCreateCount = dto.Temporary_Password_Create_Count
        if (dto.Temporary_Password_Expire != null) this.temporaryPasswordExpire = dto.Temporary_Password_Expire
        if (dto.Temporary_Password_Create_Datetime != null) this.temporaryPasswordCreateDatetime = dto.Temporary_Password_Create_Datetime
        if (dto.Modify_Datetime != null) this.modifyDatetime = dto.Modify_Datetime
        if (dto.Register_Datetime != null) this.registerDatetime = dto.Register_Datetime
    }



}