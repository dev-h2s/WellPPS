package com.wellnetworks.wellcore.domain.enums

interface ITelecomType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class TelecomType : ITelecomType{
    PRODUCT_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "TELECOM_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    PRODUCT_TYPE_NORMAL {
        override fun index() = 1
        override val key: String
            get() = "SK"

        override fun toString(): String {
            return "SK"
        }
    },
    PRODUCT_TYPE_PLAN {
        override fun index() = 2
        override val key: String
            get() = "KT"

        override fun toString(): String {
            return "KT"
        }
    },
    PRODUCT_TYPE_POSTPAID {
        override fun index() = 3
        override val key: String
            get() = "LG"

        override fun toString(): String {
            return "LG"
        }
    },
    PRODUCT_TYPE_TOPUP {
        override fun index() = 4
        override val key: String
            get() = "ETC"

        override fun toString(): String {
            return "ETC"
        }
    };

    companion object : EnumFinder<Int, TelecomType>(TelecomType.values().associateBy { it.index() })
}
