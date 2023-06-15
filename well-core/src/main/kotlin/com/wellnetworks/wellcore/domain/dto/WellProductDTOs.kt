package com.wellnetworks.wellcore.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.wellnetworks.wellcore.domain.enums.ProductType
import com.wellnetworks.wellcore.domain.enums.TelecomType
import org.hibernate.Hibernate
import java.time.ZonedDateTime

data class WellProductDTOs(
    @JsonProperty("idx")
    val Idx: String?,
    @JsonProperty("operator_name")
    val OperatorName: String?,
    @JsonProperty("operator_code")
    val OperatorCode: String,
    @JsonProperty("product_name")
    val ProductName: String?,
    @JsonProperty("product_code_in")
    val ProductCodeIn: String?,
    @JsonProperty("product_code_ex")
    val ProductCodeEx: String?,
    @JsonProperty("product_type")
    val Product_Type: ProductType,
    @JsonProperty("product_price")
    val ProductPrice: Int?,
    @JsonProperty("product_info_data")
    val ProductInfoData: String?,
    @JsonProperty("product_info_voice")
    val ProductInfoVoice: String?,
    @JsonProperty("product_info_sms")
    val ProductInfoSms: String?,
    @JsonProperty("product_info_etc")
    val ProductInfoEtc: String?,
    @JsonProperty("telecom_type")
    val Telecom_Type: TelecomType?,
    @JsonProperty("monthly")
    val Monthly: String?,
    @JsonProperty("visible_flag")
    val VisibleFlag: Boolean,
    @JsonProperty("run_flag")
    val RunFlag: Boolean,
    @JsonProperty("product_memo")
    val ProductMemo: String?,
    @JsonProperty("sort1")
    val Sort1: Int?,
    @JsonProperty("sort2")
    val Sort2: Int?,
    @JsonProperty("sort3")
    val Sort3: Int?,
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
        other as WellProductDTOs

        return Idx != null && Idx == other.Idx;
    }
}

