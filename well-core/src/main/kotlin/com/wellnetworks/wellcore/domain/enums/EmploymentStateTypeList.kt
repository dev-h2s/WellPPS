package com.wellnetworks.wellcore.domain.enums

interface IEmploymentStateType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class EmploymentStateType : IEmploymentStateType {
    EMPLOYMENT_STATE_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "EMPLOYMENT_STATE_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    EMPLOYMENT_STATE_TYPE_EMPLOYED {
        override fun index() = 1
        override val key: String
            get() = "EMPLOYMENT_STATE_TYPE_EMPLOYED"

        override fun toString(): String {
            return "재직"
        }
    },
    EMPLOYMENT_STATE_TYPE_QUIT {
        override fun index() = 2
        override val key: String
            get() = "EMPLOYMENT_STATE_TYPE_QUIT"

        override fun toString(): String {
            return "퇴사"
        }
    },
    EMPLOYMENT_STATE_TYPE_LEAVE {
        override fun index() = 3
        override val key: String
            get() = "EMPLOYMENT_STATE_TYPE_LEAVE"

        override fun toString(): String {
            return "휴직"
        }
    };

    companion object : EnumFinder<Int, EmploymentStateType>(EmploymentStateType.values().associateBy { it.index() })
}