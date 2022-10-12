package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoCreateDTO
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import org.hibernate.annotations.ColumnDefault
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "member_in_tb")

data class WellMemberInfo(
    @Id
    @Column(name = "idx", length = 16, unique = true, nullable = false)
    var idx: UUID? = UUID.randomUUID(),

    @Column(name = "user_idx", length = 32, unique = true, nullable = false)
    var user_idx: String,

    @Column(name = "table_id")
    var table_id: String,

    @Column(name = "belong", nullable = false)
    @Convert(converter = ListToStringConverter::class)
    var belong : List<String>,

    @Column(name = "name")
    var name: String,

    @Column(name = "email")
    var emil: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "well_phone")
    var well_phone: String,

    @Column(name = "jumin")
    var jumin: String,

    @Column(name = "dep")
    @Convert(converter = ListToStringConverter::class)
    var dep: List<String>,

    @Column(name = "pos")
    @Convert(converter = ListToStringConverter::class)
    var pos: List<String>,

    @Column(name = "level")
    @Convert(converter = ListToStringConverter::class)
    var level: List<String>,

    @Column(name = "addr1")
    var addr1: String,

    @Column(name = "addr2")
    var addr2: String,

    @Column(name = "bank_name")
    @Convert(converter = ListToStringConverter::class)
    var bank_name: List<String>,

    @Column(name = "b_account")
    var b_account: String,

    @Column(name = "b_holder")
    var b_holder: String,

    @Column(name = "status")
    @Convert(converter = ListToStringConverter::class)
    var status: List<String>,

    @Column(name = "type")
    @Convert(converter = ListToStringConverter::class)
    var type: List<String>,

    @Column(name = "phone_cert")
    var phone_cert: Boolean,

    @Column(name = "email_cert")
    var email_cert: Boolean,

    @Column(name = "entry_dt")
    var entry_dt: ZonedDateTime,

    @Column(name = "retire_dt")
    var retire_dt: ZonedDateTime,

    @Column(name = "retire_type")
    @Convert(converter = ListToStringConverter::class)
    var retire_type: List<String>,

    @Column(name = "access")
    var access: Boolean,

    @Column(name = "memo")
    var memo: String,

    ): BaseEntity(), UserDetails {
        override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
            return null
        }

        override fun getPassword(): String {
            return ""
        }

        override fun getUsername(): String {
            return name
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

        fun getWellMemberInfoDTO(): WellMemberInfoDTO {
            return WellMemberInfoDTO(
                idx = this.idx,
                user_idx = this.user_idx,
                table_id = this.table_id,
                belong = this.belong,
                name = this.name,
                email = this.emil,
                phone = this.phone,
                well_phone = this.well_phone,
                jumin = this.jumin,
                dep = this.dep,
                pos = this.pos,
                level = this.level,
                addr1 = this.addr1,
                addr2 = this.addr2,
                bank_name = this.bank_name,
                b_account = this.b_account,
                b_holder = this.b_holder,
                status = this.status,
                type = this.type,
                phone_cert = this.phone_cert,
                email_cert = this.email_cert,
                entry_dt = this.entry_dt,
                retire_dt = this.retire_dt,
                retire_type = this.retire_type,
                access = this.access,
                memo = this.memo,
                modify_datetime = this.modify_datetime,
                register_datetime = this.register_datetime,
            )
        }

        fun createWellMemberInfoDTO(): WellMemberInfoCreateDTO {
            return WellMemberInfoCreateDTO(
                user_idx = this.user_idx,
                table_id = this.table_id,
                belong = this.belong,
                name = this.name,
                email = this.emil,
                phone = this.phone,
                well_phone = this.well_phone,
                jumin = this.jumin,
                dep = this.dep,
                pos = this.pos,
                level = this.level,
                addr1 = this.addr1,
                addr2 = this.addr2,
                bank_name = this.bank_name,
                b_account = this.b_account,
                b_holder = this.b_holder,
                status = this.status,
                type = this.type,
                phone_cert = this.phone_cert,
                email_cert = this.email_cert,
                entry_dt = this.entry_dt,
                retire_dt = this.retire_dt,
                retire_type = this.retire_type,
                access = this.access,
                memo = this.memo,
                modify_datetime = this.modify_datetime,
                register_datetime = this.register_datetime,
            )
        }

}
