package com.wellnetworks.wellcore.domain

import io.micrometer.core.annotation.Counted
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(name = "opening_tb", indexes =
[
    Index(name = "IX_opening", columnList = "idx, user_idx, user_sub_idx, opening_type, mvno_code, product_code, phone_no", unique = false),
])
data class WellOpeningEntity(
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    @Column(name = "user_idx", columnDefinition = "uniqueidentifier", nullable = false )
    var userIdx: String,

    @Column(name = "user_sub_idx", columnDefinition = "uniqueidentifier")
    var userSubIdx: String?,

    @Column(name = "opening_type")
    var openingType: String?,

    @Column(name = "operator_code",  length = 32, nullable = true)
    var operatorCode: String?,

    @Column(name="product_code_in", length = 32, nullable = true)
    var productCodeIn: String?,

    @Column(name = "phone_no",  length = 32, nullable = true)
    var phoneNo: String?,

    @Column(name = "customer_name", length = 64, nullable = true)
    var customerName: String?,

    @Column(name = "passport", nullable = true)
    var passport: Byte?,

    @Column(name = "country", length = 32, nullable = true)
    var country: String?,

    @Column(name = "model_no", length = 32, nullable = true)
    var model_no: String?,

    @Column(name = "payment_type", nullable = true)
    var paymentType: Byte?,

    @Column(name = "local_type", nullable = true)
    var localType: Byte?,

    @Column(name = "incharge", length = 32, nullable = true)
    var incharge: String?,

    @Column(name = "user_name", length = 32, nullable = true)
    var userName: String?,

    @Column(name = "user_id", length = 32, nullable = true)
    var userId: String?,

    @Column(name = "check", nullable = true)
    var check: Byte?,

    @Column(name = "inspector", length = 32, nullable = true)
    var inspector: String?,

    @Column(name = "auto_charge", nullable = true)
    var autoCharge: Byte?,

    @Column(name = "write_type", nullable = true)
    var writeType: Byte?,

    @Column(name="commission1", nullable = true)
    var commission1: Int?,

    @Column(name="commission2", nullable = true)
    var commission2: Int?,

    @Column(name="commission3", nullable = true)
    var commission3: Int?,

    ): BaseEntity() {


}