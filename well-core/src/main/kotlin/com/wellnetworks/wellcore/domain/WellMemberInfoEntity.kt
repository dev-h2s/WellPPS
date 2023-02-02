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
 [Index(name = "IX_tid", columnList = "tbl_id ASC", unique = false),
 ])
data class WellMemberInfoEntity(
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

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

    @Column(name = "phone_cert", columnDefinition = "bit")
    var certificationPhone: Boolean,

    @Column(name = "email_cert", columnDefinition = "bit")
    var certificationEmail: Boolean,

    @Column(name = "entry_dt")
    var entryDatetime: ZonedDateTime,

    @Column(name = "retire_dt")
    var employmentQuitDatetime: ZonedDateTime,

    @Column(name = "retire_type")
    @Convert(converter = EmploymentQuitTypeToIndexConverter::class)
    var employmentQuitType: EmploymentQuitType,

    @Column(name = "access", columnDefinition = "bit")
    var access: Boolean,

    @Column(name="file1_idx", nullable = true)
    var file1Idx: String?,

    @Column(name="file2_idx", nullable = true)
    var file2Idx: String?,

    @Column(name="file3_idx", nullable = true)
    var file3Idx: String?,

    @Column(name="file4_idx", nullable = true)
    var file4Idx: String?,

    @Column(name="file5_idx", nullable = true)
    var file5Idx: String?,

    @Column(name = "memo")
    var memo: String,

    ): BaseEntity() {

    fun toDto(): WellMemberInfoDTO {
        return WellMemberInfoDTO(
            Idx = this.idx.uppercase(),
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
            Member_File1_idx = this.file1Idx.toString().uppercase(),
            Member_File2_idx = this.file2Idx.toString().uppercase(),
            Member_File3_idx = this.file3Idx.toString().uppercase(),
            Member_File4_idx = this.file4Idx.toString().uppercase(),
            Member_File5_idx = this.file5Idx.toString().uppercase(),
            Memo = this.memo,
            Modify_Datetime = this.modifyDatetime,
            Register_Datetime = this.registerDatetime,
        )
 1   }

    fun fromDto(dto: WellMemberInfoDTO) {
        this.tableID = dto.Table_ID
        this.currentEmployment = dto.Current_Employment
        this.name = dto.Name
        this.email = dto.Email
        this.phonePrivate = dto.Phone_Private
        this.phoneWork = dto.Phone_Work
        this.registrationNumber = dto.Registration_Number
        this.department = dto.Department
        this.jobPosition = dto.Job_Position
        this.level = dto.Level
        this.homeAddress1 = dto.Home_Address1
        this.homeAddress2 = dto.Home_Address2
        this.bankName = dto.Bank_Name
        this.bankAccount = dto.Bank_Account
        this.bankHolder = dto.Bank_Holder
        this.employmentState = dto.Employment_State
        this.jobType = dto.Job_Type
        this.certificationPhone = dto.Certification_Phone
        this.certificationEmail = dto.Certification_Email
        this.entryDatetime = dto.Entry_Datetime
        this.employmentQuitDatetime = dto.Employment_Quit_Datetime
        this.employmentQuitType = dto.Employment_Quit_Type
        this.access = dto.Access
        this.file1Idx = dto.Member_File1_idx?.uppercase()
        this.file2Idx = dto.Member_File2_idx?.uppercase()
        this.file3Idx = dto.Member_File3_idx?.uppercase()
        this.file4Idx = dto.Member_File4_idx?.uppercase()
        this.file5Idx = dto.Member_File5_idx?.uppercase()
        this.memo = dto.Memo.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellPartnerEntity

        return idx != null && idx.uppercase() == other.idx.uppercase();
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    override fun toString(): String {
        return name;
    }

}
