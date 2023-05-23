package com.wellnetworks.wellcore.domain.enums

interface IProductType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class ProductType : IProductType{
    PRODUCT_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "PRODUCT_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    PRODUCT_TYPE_NORMAL {
        override fun index() = 1
        override val key: String
            get() = "PRODUCT_TYPE_NORMAL"

        override fun toString(): String {
            return "종량요금제"
        }
    },
    PRODUCT_TYPE_PLAN {
        override fun index() = 2
        override val key: String
            get() = "PRODUCT_TYPE_PLAN"

        override fun toString(): String {
            return "정액요금제"
        }
    },
    PRODUCT_TYPE_POSTPAID {
        override fun index() = 3
        override val key: String
            get() = "PRODUCT_TYPE_POSTPAID"

        override fun toString(): String {
            return "후불"
        }
    },
    PRODUCT_TYPE_TOPUP {
        override fun index() = 4
        override val key: String
            get() = "PRODUCT_TYPE_TOPUP"

        override fun toString(): String {
            return "탑업"
        }
    },
    PRODUCT_TYPE_INTERNATIONAL {
        override fun index() = 5
        override val key: String
            get() = "PRODUCT_TYPE_INTERNATIONAL"

        override fun toString(): String {
            return "국제전화"
        }
    },
    PRODUCT_TYPE_ETC {
        override fun index() = 6
        override val key: String
            get() = "PRODUCT_TYPE_ETC"

        override fun toString(): String {
            return "기타"
        }
    };

    companion object : EnumFinder<Int, ProductType>(ProductType.values().associateBy { it.index() })
}