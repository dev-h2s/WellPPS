package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.*
import com.wellnetworks.wellcore.domain.dto.WellProductDTOUpdate
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.domain.enums.*
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.ZonedDateTime

@Entity
@Table(name = "product_tb", indexes =
[
    Index(name = "IX_idx", columnList = "idx,operator_name, product_name", unique = false),
])
data class WellProductEntity(
    @Id
    @Column(name="idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    @Column(name="operator_name", length = 32, nullable = true)
    var operatorName: String?,

    @Column(name="operator_code", length = 32, nullable = true)
    var operatorCode: String,

    @Column(name="product_name", length = 64, nullable = true)
    var productName: String?,

    @Column(name="product_code_in", length = 32, nullable = true)
    var productCodeIn: String?,

    @Column(name="product_code_ex", length = 32, nullable = true)
    var productCodeEx: String?,

    @Convert(converter = ProductTypeToIndexConverter::class)
    var product: ProductType?,

    @Column(name="product_price", nullable = true)
    var productPrice: Int?,

    @Column(name="product_info_data", nullable = true)
    var productInfoData: String?,

    @Column(name="product_info_voice", nullable = true)
    var productInfoVoice: String?,

    @Column(name="product_info_sms", nullable = true)
    var productInfoSms: String?,

    @Column(name="product_info_etc", nullable = true)
    var productInfoEtc: String?,

    @Convert(converter = TelecomTypeToIndexConverter::class)
    var telecom: TelecomType?,

    @Column(name="monthly", nullable = true)
    var monthly: String?,

    @Column(name="visible_flag", columnDefinition = "bit", nullable = false)
    var visibleFlag: Boolean,

    @Column(name="run_flag", columnDefinition = "bit", nullable = false)
    var runFlag: Boolean,

    @Column(name="product_memo", nullable = true)
    var productMemo: String?,

    @Column(name="sort1", columnDefinition = "bit", nullable = true)
    var sort1: Int?,

    @Column(name="sort2", columnDefinition = "bit", nullable = true)
    var sort2: Int,

    @Column(name="sort3", columnDefinition = "bit", nullable = true)
    var sort3: Int,

    @Column(name="product_regdt", nullable = true)
    var productRegisterDatetime: ZonedDateTime?,

    @Column(name="product_moddt", nullable = true)
    var productModifyDatetime: ZonedDateTime?,
): BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellProductEntity

        return idx != null && idx.uppercase() == other.idx.uppercase();
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    override fun toString(): String {
        return productName ?: ""
    }

    fun toDto(): WellProductDTOs {
        return WellProductDTOs(
            Idx = this.idx.uppercase(),
            OperatorName = this.operatorName,
            OperatorCode = this.operatorCode,
            ProductName = this.productName,
            ProductCodeIn = this.productCodeIn,
            ProductCodeEx = this.productCodeEx,
            Product_Type = this.product,
            ProductPrice = this.productPrice,
            ProductInfoData = this.productInfoData,
            ProductInfoVoice = this.productInfoVoice,
            ProductInfoSms = this.productInfoSms,
            ProductInfoEtc = this.productInfoEtc,
            Telecom_Type = this.telecom,
            VisibleFlag = this.visibleFlag,
            Monthly = this.monthly,
            RunFlag = this.runFlag,
            ProductMemo = this.productMemo,
            Sort1 = this.sort1,
            Sort2 = this.sort2,
            Sort3 = this.sort3,
            Modify_Datetime = this.productModifyDatetime,
            Register_Datetime = this.productRegisterDatetime,
        )
    }

    fun updateDto(dto: WellProductDTOUpdate) {
        if (dto.Idx != null) this.idx = dto.Idx
        if (dto.OperatorName != null) this.operatorName = dto.OperatorName
        if (dto.OperatorCode != null) this.operatorCode = dto.OperatorCode
        if (dto.ProductName != null) this.productName = dto.ProductName
        if (dto.ProductCodeIn != null) this.productCodeIn = dto.ProductCodeIn
        if (dto.ProductCodeEx != null) this.productCodeEx = dto.ProductCodeEx
        if (dto.Product_Type != null) this.product = dto.Product_Type
        if (dto.ProductPrice != null) this.productPrice = dto.ProductPrice
        if (dto.ProductInfoData != null) this.productInfoData = dto.ProductInfoData
        if (dto.ProductInfoVoice != null) this.productInfoVoice = dto.ProductInfoVoice
        if (dto.ProductInfoSms != null) this.productInfoSms = dto.ProductInfoSms
        if (dto.ProductInfoEtc != null) this.productInfoEtc = dto.ProductInfoEtc
        if (dto.Telecom_Type != null) this.telecom = dto.Telecom_Type
        if (dto.VisibleFlag != null) this.visibleFlag = dto.VisibleFlag
        if (dto.RunFlag != null) this.runFlag = dto.RunFlag
        if (dto.Monthly != null) this.monthly = dto.Monthly
        if (dto.ProductMemo != null) this.productMemo = dto.ProductMemo
        if (dto.Sort1 != null) this.sort1 = dto.Sort1
        if (dto.Sort2 != null) this.sort2 = dto.Sort2
        if (dto.Sort3 != null) this.sort3 = dto.Sort3
        if (dto.Modify_Datetime != null) this.productModifyDatetime = dto.Modify_Datetime
        if (dto.Register_Datetime != null) this.productRegisterDatetime = dto.Register_Datetime


    }


}
