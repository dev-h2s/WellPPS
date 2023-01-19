package com.wellnetworks.wellcore.domain.enums

interface IAgreeType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class AgreeType : IAgreeType {
    AGREE_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "AGREE_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알 수 없음"
        }
    },
    AGREE_TYPE_AGREE {
        override fun index() = 1
        override val key: String
            get() = "AGREE_TYPE_AGREE"

        override fun toString(): String {
            return "동의"
        }
    },
    AGREE_TYPE_DISAGREE {
        override fun index() = 2
        override val key: String
            get() = "AGREE_TYPE_DISAGREE"

        override fun toString(): String {
            return "비동의"
        }
    };

    companion object : EnumFinder<Int, AgreeType>(AgreeType.values().associateBy { it.index() })
}