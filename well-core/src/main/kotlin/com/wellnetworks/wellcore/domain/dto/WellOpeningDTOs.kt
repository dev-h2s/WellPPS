package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.wellnetworks.wellcore.domain.enums.LocalType
import com.wellnetworks.wellcore.domain.enums.OpeningType
import com.wellnetworks.wellcore.domain.enums.PaymentType
import com.wellnetworks.wellcore.domain.enums.WriteType
import org.hibernate.Hibernate
import java.time.ZonedDateTime

data class WellOpeningDTOs(
    @JsonProperty("idx")
    val Idx: String?,
    @JsonProperty("user_idx")
    val UserIdx: String?,
    @JsonProperty("user_sub_idx")
    val UserSubIdx: String?,
    @JsonProperty("opening_type")
    val OpeningType: OpeningType?,
    @JsonProperty("operator_code")
    val OperatorCode: String?,
    @JsonProperty("product_code_in")
    val ProductCodeIn: String?,
    @JsonProperty("phone_num")
    val PhoneNum: String?,
    @JsonProperty("customer_name")
    val CustomerName: String?,
    @JsonProperty("passport")
    val Passport: Byte?,
    @JsonProperty("country")
    val Country: String?,
    @JsonProperty("model_no")
    val ModelNo: String?,
    @JsonProperty("payment_type")
    val PaymentType: PaymentType?,
    @JsonProperty("local_type")
    val LocalType: LocalType?,
    @JsonProperty("incharge")
    val Incharge: String?,
    @JsonProperty("user_name")
    val UserName: String?,
    @JsonProperty("user_id")
    val UserId: String?,
    @JsonProperty("check")
    val Check: Byte?,
    @JsonProperty("inspector")
    val Inspector: String?,
    @JsonProperty("auto_charge")
    val AutoCharge: Byte?,
    @JsonProperty("write_type")
    val WriteType: WriteType?,
    @JsonProperty("commission1")
    val Commission1: Int?,
    @JsonProperty("commission2")
    val Commission2: Int?,
    @JsonProperty("commission3")
    val Commission3: Int?,
    @JsonIgnore
    override val Modify_Datetime: ZonedDateTime?,
    @JsonIgnore
    override val Register_Datetime: ZonedDateTime?,
): BaseDTO(Modify_Datetime, Register_Datetime) {


    override fun hashCode(): Int {
        return Idx.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellOpeningDTOs

        return Idx != null && Idx == other.Idx;
    }
}


