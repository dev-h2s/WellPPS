package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.*
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "member_info_tb", indexes =
 [Index(name = "IX_user_idx", columnList = "user_idx ASC", unique = true),
 ])
data class WellMemberInfoEntity(
    @Id
    @Column(name = "idx", unique = true, nullable = false)
    var Idx: UUID,

    @Column(name = "user_idx", unique = true, nullable = false)
    var UserIdx: UUID,

    @Column(name = "tbl_id", length = 16)
    var TableID: String,

    @Column(name = "belong", nullable = false)
    @Convert(converter = CurrentEmploymentTypeToIndexConverter::class)
    var CurrentEmployment : CurrentEmploymentType,

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
    @Convert(converter = DepartmentTypeToIndexConverter::class)
    var Department: DepartmentType,

    @Column(name = "pos")
    @Convert(converter = JobPositionTypeToIndexConverter::class)
    var JobPosition: JobPositionType,

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
    @Convert(converter = EmploymentStateTypeToIndexConverter::class)
    var EmploymentState: EmploymentStateType,

    @Column(name = "j_type")
    @Convert(converter = JobTypeToIndexConverter::class)
    var JobType: JobType,

    @Column(name = "phone_cert")
    var CertificationPhone: Boolean,

    @Column(name = "email_cert")
    var CertificationEmail: Boolean,

    @Column(name = "entry_dt")
    var EntryDatetime: ZonedDateTime,

    @Column(name = "retire_dt")
    var EmploymentQuitDatetime: ZonedDateTime,

    @Column(name = "retire_type")
    @Convert(converter = EmploymentQuitTypeToIndexConverter::class)
    var EmploymentQuitType: EmploymentQuitType,

    @Column(name = "access")
    var Access: Boolean,

    @Column(name = "memo")
    var Memo: String,

    ): BaseEntity() {

    fun getWellMemberInfoDTO(): WellMemberInfoDTO {
        return WellMemberInfoDTO(
            Idx = this.Idx,
            User_Idx = this.UserIdx,
            Table_ID = this.TableID,
            Current_Employment = this.CurrentEmployment,
            Name = this.Name,
            Email = this.Email,
            Phone_Private = this.PhonePrivate,
            Phone_Work = this.PhoneWork,
            Registration_Number = this.RegistrationNumber,
            Department = this.Department,
            Job_Position = this.JobPosition,
            Level = this.Level,
            Home_Address1 = this.HomeAddress1,
            Home_Address2 = this.HomeAddress2,
            Bank_Name = this.BankName,
            Bank_Account = this.BankAccount,
            Bank_Holder = this.BankHolder,
            Employment_State = this.EmploymentState,
            Job_Type = this.JobType,
            Certification_Phone = this.CertificationPhone,
            Certification_Email = this.CertificationEmail,
            Entry_Datetime = this.EntryDatetime,
            Employment_Quit_Datetime = this.EmploymentQuitDatetime,
            Employment_Quit_Type = this.EmploymentQuitType,
            Access = this.Access,
            Memo = this.Memo,
            Modify_Datetime = this.ModifyDatetime,
            Register_Datetime = this.RegisterDatetime,
        )
 1   }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerEntity

        return Idx != null && Idx == other.Idx;
    }

    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun toString(): String {
        return Name;
    }

}
