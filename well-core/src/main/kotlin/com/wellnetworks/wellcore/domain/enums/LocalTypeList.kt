package com.wellnetworks.wellcore.domain.enums

interface ILocalType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class LocalType : ILocalType{
    LOCAL_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "LOCAL_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },

    LOCAL_TYPE_NATIVE {
        override fun index() = 1
        override val key: String
            get() = "LOCAL_TYPE_NATIVE"

        override fun toString(): String {
            return "내국인"
        }
    },

    LOCAL_TYPE_FOREIGNER {
        override fun index() = 2
        override val key: String
            get() = "LOCAL_TYPE_FOREIGNER"

        override fun toString(): String {
            return "외국인"
        }
    };

    companion object : EnumFinder<Int, LocalType>(LocalType.values().associateBy { it.index() })
}