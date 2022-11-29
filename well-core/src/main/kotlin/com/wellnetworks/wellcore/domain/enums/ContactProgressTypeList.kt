package com.wellnetworks.wellcore.domain.enums

interface IContactProgressType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class ContactProgressType : IContactProgressType {
    CONTACT_PROGRESS_TYPE_UNKNOWN {
        override fun index() = -1
        override val key: String
            get() = "CONTACT_PROGRESS_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    CONTACT_PROGRESS_TYPE_WAITING {
        override fun index() = 1
        override val key: String
            get() = "CONTACT_PROGRESS_TYPE_WAITING"

        override fun toString(): String {
            return "대기"
        }
    },
    CONTACT_PROGRESS_TYPE_APPROVED {
        override fun index() = 2
        override val key: String
            get() = "CONTACT_PROGRESS_TYPE_APPROVED"

        override fun toString(): String {
            return "승인"
        }
    },
    CONTACT_PROGRESS_TYPE_REJECT {
        override fun index() = 3
        override val key: String
            get() = "CONTACT_PROGRESS_TYPE_REJECT"

        override fun toString(): String {
            return "거부"
        }
    };

    companion object : EnumFinder<Int, ContactProgressType>(ContactProgressType.values().associateBy { it.index() })
}