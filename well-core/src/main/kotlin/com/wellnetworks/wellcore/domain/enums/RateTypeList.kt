package com.wellnetworks.wellcore.domain.enums

interface IRateType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class RateType : IRateType {
    RATE_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "RATE_TYPE_UNKNOWN"

        override fun toString(): String {
            return "미설정"
        }
    },
    RATE_TYPE_OPENING {
        override fun index() = 1
        override val key: String
            get() = "RATE_TYPE_OPENING"

        override fun toString(): String {
            return "개통점"
        }
    },
    RATE_TYPE_TOP_UP {
        override fun index() = 2
        override val key: String
            get() = "RATE_TYPE_TOP_UP"

        override fun toString(): String {
            return "충전점"
        }
    },
    RATE_TYPE_API {
        override fun index() = 3
        override val key: String
            get() = "RATE_TYPE_API"

        override fun toString(): String {
            return "API"
        }
    };

    companion object : EnumFinder<Int, RateType>(RateType.values().associateBy { it.index() })
}