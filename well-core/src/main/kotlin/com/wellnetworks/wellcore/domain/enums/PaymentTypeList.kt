package com.wellnetworks.wellcore.domain.enums

interface IPaymentType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class PaymentType : IPaymentType {
    PAYMENT_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "PAYMENT_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    PAYMENT_TYPE_PREPAID {
        override fun index() = 1
        override val key: String
            get() = "PAYMENT_TYPE_PREPAID"

        override fun toString(): String {
            return "선불"
        }
    },
    PAYMENT_TYPE_POSTPAID {
        override fun index() = 2
        override val key: String
            get() = "PAYMENT_TYPE_POSTPAID"

        override fun toString(): String {
            return "후불"
        }
    };
    companion object : EnumFinder<Int, PaymentType>(PaymentType.values().associateBy { it.index() })
}