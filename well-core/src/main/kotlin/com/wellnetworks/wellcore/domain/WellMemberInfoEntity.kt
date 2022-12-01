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
    var idx: UUID,

    @Column(name = "user_idx", unique = true, nullable = false)
    var userIdx: UUID,

    @Column(name = "tbl_id", length = 16)
    var tableID: String,

    @Column(name = "belong", nullable = false)
    @Convert(converter = CurrentEmploymentTypeToIndexConverter::class)
    var currentEmployment : CurrentEmploymentType,

    @Column(name = "name", length = 64, nullable = false)
    var name: String,

    @Column(name = "mail", length = 320, nullable = false)
    var email: String,

    @Column(name = "phone_private", length = 16)
    var phonePrivate: String,

    @Column(name = "phone_work", length = 16)
    var phoneWork: String,

    @Column(name = "reg_num", length = 16)
    var registrationNumber: String,

    @Column(name = "dep")
    @Convert(converter = DepartmentTypeToIndexConverter::class)
    var department: DepartmentType,

    @Column(name = "pos")
    @Convert(converter = JobPositionTypeToIndexConverter::class)
    var jobPosition: JobPositionType,

    @Column(name = "level")
    var level: Byte,

    @Column(name = "home_addr1", length = 255)
    var homeAddress1: String,

    @Column(name = "home_addr2", length = 255)
    var homeAddress2: String,

    @Column(name = "b_name", length = 64)
    var bankName: String,

    @Column(name = "b_account", length = 64)
    var bankAccount: String,

    @Column(name = "b_holder", length = 64)
    var bankHolder: String,

    @Column(name = "state")
    @Convert(converter = EmploymentStateTypeToIndexConverter::class)
    var employmentState: EmploymentStateType,

    @Column(name = "j_type")
    @Convert(converter = JobTypeToIndexConverter::class)
    var jobType: JobType,

    @Column(name = "phone_cert")
    var certificationPhone: Boolean,

    @Column(name = "email_cert")
    var certificationEmail: Boolean,

    @Column(name = "entry_dt")
    var entryDatetime: ZonedDateTime,

    @Column(name = "retire_dt")
    var employmentQuitDatetime: ZonedDateTime,

    @Column(name = "retire_type")
    @Convert(converter = EmploymentQuitTypeToIndexConverter::class)
    var employmentQuitType: EmploymentQuitType,

    @Column(name = "access")
    var access: Boolean,

    @Column(name = "memo")
    var memo: String,

    ): BaseEntity() {

    fun getWellMemberInfoDTO(): WellMemberInfoDTO {
        return WellMemberInfoDTO(
            Idx = this.idx,
            User_Idx = this.userIdx,
            Table_ID = this.tableID,
            Current_Employment = this.currentEmployment,
            Name = this.name,
            Email = this.email,
            Phone_Private = this.phonePrivate,
            Phone_Work = this.phoneWork,
            Registration_Number = this.registrationNumber,
            Department = this.department,
            Job_Position = this.jobPosition,
            Level = this.level,
            Home_Address1 = this.homeAddress1,
            Home_Address2 = this.homeAddress2,
            Bank_Name = this.bankName,
            Bank_Account = this.bankAccount,
            Bank_Holder = this.bankHolder,
            Employment_State = this.employmentState,
            Job_Type = this.jobType,
            Certification_Phone = this.certificationPhone,
            Certification_Email = this.certificationEmail,
            Entry_Datetime = this.entryDatetime,
            Employment_Quit_Datetime = this.employmentQuitDatetime,
            Employment_Quit_Type = this.employmentQuitType,
            Access = this.access,
            Memo = this.memo,
            Modify_Datetime = this.modifyDatetime,
            Register_Datetime = this.registerDatetime,
        )
 1   }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerEntity

        return idx != null && idx == other.idx;
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    override fun toString(): String {
        return name;
    }

}
