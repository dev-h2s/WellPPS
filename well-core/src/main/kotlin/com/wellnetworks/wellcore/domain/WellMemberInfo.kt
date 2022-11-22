package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoCreateDTO
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import org.hibernate.Hibernate
import org.hibernate.annotations.ColumnDefault
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "member_info_tb", indexes =
 [Index(name = "IX_user_idx", columnList = "user_idx ASC", unique = true),
 ])
data class WellMemberInfo(
    @Id
    @Column(name = "idx", unique = true, nullable = false)
    var Idx: UUID? = UUID.randomUUID(),

    @Column(name = "user_idx", unique = true, nullable = false)
    var UserIdx: UUID,

    @Column(name = "tbl_id", length = 16)
    var TableID: String,

    @Column(name = "belong", nullable = false)
    var Belong : Byte,

    @Column(name = "name", length = 64, nullable = false)
    var Name: String,

    @Column(name = "mail", length = 320, nullable = false)
    var Email: String,

    @Column(name = "phone_private", length = 16)
    var PhonePrivate: String,

    @Column(name = "phone_work", length = 16)
    var PhoneWork: String,

    @Column(name = "reg_num", length = 16)
    var RegistrationNumber: String,

    @Column(name = "dep")
    var Department: Byte,

    @Column(name = "pos")
    var JobPosition: Byte,

    @Column(name = "level")
    var Level: Byte,

    @Column(name = "home_addr1", length = 255)
    var HomeAddress1: String,

    @Column(name = "home_addr2", length = 255)
    var HomeAddress2: String,

    @Column(name = "b_name", length = 64)
    var BankName: String,

    @Column(name = "b_account", length = 64)
    var BankAccount: String,

    @Column(name = "b_holder", length = 64)
    var BankHolder: String,

    @Column(name = "state")
    var State: Byte,

    @Column(name = "j_type")
    var JobType: Byte,

    @Column(name = "phone_cert")
    var CertificationPhone: Byte,

    @Column(name = "email_cert")
    var CertificationEmail: Byte,

    @Column(name = "entry_dt")
    var EntryDatetime: ZonedDateTime,

    @Column(name = "retire_dt")
    var RetireDatetime: ZonedDateTime,

    @Column(name = "retire_type")
    var RetireType: Byte,

    @Column(name = "access")
    var Access: Byte,

    @Column(name = "memo")
    var Memo: String,

    ): BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartner

        return Idx != null && Idx == other.Idx;
    }

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun toString(): String {
        return Name;
    }

}
