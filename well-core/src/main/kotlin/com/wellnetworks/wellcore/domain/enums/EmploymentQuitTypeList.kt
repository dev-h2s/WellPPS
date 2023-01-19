package com.wellnetworks.wellcore.domain.enums

interface IEmploymentQuitType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class EmploymentQuitType : IEmploymentQuitType {
    EMPLOYMENT_QUIT_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = ""

        override fun toString(): String {
            return ""
        }
    },
    EMPLOYMENT_QUIT_TYPE_VOLUNTARY_TERMINATION {
        override fun index() = 1
        override val key: String
            get() = ""

        override fun toString(): String {
            return "자진퇴사"
        }
    },
    EMPLOYMENT_QUIT_TYPE_PERSONAL_REASONS {
        override fun index() = 2
        override val key: String
            get() = "EMPLOYMENT_QUIT_TYPE_PERSONAL_REASONS"

        override fun toString(): String {
            return "개인사정"
        }
    },
    EMPLOYMENT_QUIT_TYPE_CONTRACT_EXPIRATION {
        override fun index() = 3
        override val key: String
            get() = ""

        override fun toString(): String {
            return "계약만료"
        }
    },
    EMPLOYMENT_QUIT_TYPE_ADVICE_TO_RESIGN {
        override fun index() = 4
        override val key: String
        get() = ""

        override fun toString(): String {
            return "권고사직"
        }
    },
    EMPLOYMENT_QUIT_TYPE_RETIREMENT {
        override fun index() = 5
        override val key: String
            get() = ""

        override fun toString(): String {
            return "은퇴"
        }
    };

    companion object : EnumFinder<Int, EmploymentQuitType>(EmploymentQuitType.values().associateBy { it.index() })
}