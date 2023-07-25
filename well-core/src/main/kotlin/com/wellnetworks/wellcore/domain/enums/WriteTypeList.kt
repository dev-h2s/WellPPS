package com.wellnetworks.wellcore.domain.enums

interface IWriteType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class WriteType : IWriteType{
    WRITE_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "WRITE_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    WRITE_TYPE_PC {
        override fun index() = 1
        override val key: String
            get() = "WRITE_TYPE_PC"

        override fun toString(): String {
            return "PC"
        }
    },
    WRITE_TYPE_MOBILE {
        override fun index() = 2
        override val key: String
            get() = "WRITE_TYPE_MOBILE"

        override fun toString(): String {
            return "모바일"
        }
    },
    WRITE_TYPE_APP {
        override fun index() = 3
        override val key: String
            get() = "WRITE_TYPE_APP"

        override fun toString(): String {
            return "어플"
        }
    },
    WRITE_TYPE_API {
        override fun index() = 4
        override val key: String
            get() = "WRITE_TYPE_API"

        override fun toString(): String {
            return "API"
        }
    },
    WRITE_TYPE_ETC {
        override fun index() = 5
        override val key: String
            get() = "WRITE_TYPE_ETC"

        override fun toString(): String {
            return "기타"
        }
    };
    companion object : EnumFinder<Int, WriteType>(WriteType.values().associateBy { it.index() })
}