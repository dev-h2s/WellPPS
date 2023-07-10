package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.*
import com.wellnetworks.wellcore.domain.enums.LocalType
import com.wellnetworks.wellcore.domain.enums.OpeningType
import com.wellnetworks.wellcore.domain.enums.PaymentType
import com.wellnetworks.wellcore.domain.enums.WriteType
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "opening_tb", indexes =
[
    Index(name = "IX_opening", columnList = "idx, user_idx, user_sub_idx, opening_type, operator_code, product_code_in, phone_num", unique = false),
])
data class WellOpeningEntity(
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    @Column(name = "user_idx", nullable = false )
    var userIdx: String,

    @Column(name = "user_sub_idx", nullable = true )
    var userSubIdx: String?,

    @Column(name = "opening_type")
    @Convert(converter = OpeningTypeToIndexConverter::class)
    var openingType: OpeningType?,

    @Column(name = "operator_code",  length = 32, nullable = true)
    var operatorCode: String?,

    @Column(name="product_code_in", length = 32, nullable = true)
    var productCodeIn: String?,

    @Column(name = "phone_num",  length = 32, nullable = true)
    var phoneNum: String?,

    @Column(name = "customer_name", length = 64, nullable = true)
    var customerName: String?,

    @Column(name = "passport", columnDefinition = "bit", nullable = true)
    var passport: Boolean?,

    @Column(name = "country", length = 32, nullable = true)
    var country: String?,

    @Column(name = "model_no", length = 32, nullable = true)
    var model_no: String?,

    @Column(name = "payment_type", nullable = true)
    @Convert(converter = PaymentTypeToIndexConverter::class)
    var paymentType: PaymentType?,

    @Column(name = "local_type", nullable = true)
    @Convert(converter = LocalTypeToIndexConverter::class)
    var localType: LocalType?,

    @Column(name = "incharge", length = 32, nullable = true)
    var incharge: String?,

    @Column(name = "user_name", length = 32, nullable = true)
    var userName: String?,

    @Column(name = "user_id", length = 32, nullable = true)
    var userId: String?,

    @Column(name = "check_review", columnDefinition = "bit", nullable = true)
    var checkReview: Boolean?,

    @Column(name = "inspector", length = 32, nullable = true)
    var inspector: String?,

    @Column(name = "auto_charge", columnDefinition = "bit", nullable = true)
    var autoCharge: Boolean?,

    @Column(name = "write_type", nullable = true)
    @Convert(converter = WriteTypeToIndexConverter::class)
    var writeType: WriteType?,

    @Column(name="commission1", nullable = true)
    var commission1: Int?,

    @Column(name="commission2", nullable = true)
    var commission2: Int?,

    @Column(name="commission3", nullable = true)
    var commission3: Int?,

): BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellOpeningEntity

        return idx != null && idx.uppercase() == other.idx.uppercase()
    }

    override fun hashCode(): Int {
        return idx.hashCode()
    }

    override fun toString(): String {
        return productCodeIn ?: ""
    }

    fun toDto(): WellOpeningDTOs {
        return WellOpeningDTOs(
            Idx = this.idx.uppercase(),
            UserIdx = this.userIdx.uppercase(),
            UserSubIdx = this.userSubIdx?.uppercase(),
            OpeningType = this.openingType,
            OperatorCode = this.operatorCode,
            ProductCodeIn = this.productCodeIn,
            PhoneNum = this.phoneNum,
            CustomerName = this.customerName,
            Passport = this.passport,
            Country = this.country,
            ModelNo = this.model_no,
            PaymentType = this.paymentType,
            LocalType = this.localType,
            Incharge = this.incharge,
            UserName = this.userName,
            UserId = this.userId,
            CheckReview = this.checkReview,
            Inspector = this.inspector,
            AutoCharge = this.autoCharge,
            WriteType = this.writeType,
            Commission1 = this.commission1,
            Commission2 = this.commission2,
            Commission3 = this.commission3,
            Modify_Datetime = this.modifyDatetime,
            Register_Datetime = this.registerDatetime
        )
    }

}