package com.wellnetworks.wellcore.domain.enums

interface IContactType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class ContactType : IContactType {
    CONTACT_TYPE_UNKNOWN {
        override fun index() = -1
        override val key: String
            get() = "CONTACT_TYPE_UNKNOWN"

        override fun toString(): String {
            return "미설정"
        }
    },
    CONTACT_TYPE_OPENING {
        override fun index() = 1
        override val key: String
            get() = "CONTACT_TYPE_OPENING"

        override fun toString(): String {
            return "개통점"
        }
    },
    CONTACT_TYPE_TOP_UP {
        override fun index() = 2
        override val key: String
            get() = "CONTACT_TYPE_TOP_UP"

        override fun toString(): String {
            return "충전점"
        }
    };

    companion object : EnumFinder<Int, ContactType>(ContactType.values().associateBy { it.index() })
}