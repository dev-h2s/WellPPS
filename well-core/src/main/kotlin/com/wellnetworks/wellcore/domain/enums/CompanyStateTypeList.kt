package com.wellnetworks.wellcore.domain.enums

interface ICompanyStateType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class CompanyStateType : ICompanyStateType {
    COMPANY_STATE_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "COMPANY_STATE_TYPE_UNKNOWN"

        override fun toString(): String {
            return "상태없음"
        }
    },
    COMPANY_STATE_TYPE_REGISTERED {
        override fun index() = 1
        override val key: String
            get() = "COMPANY_STATE_TYPE_REGISTERED"

        override fun toString(): String {
            return "등록됨"
        }
    },
    COMPANY_STATE_TYPE_TEMPORARY_REGISTRATION {
        override fun index() = 2
        override val key: String
            get() = "COMPANY_STATE_TYPE_TEMPORARY_REGISTRATION"

        override fun toString(): String {
            return "가등록"
        }
    },
    COMPANY_STATE_TYPE_WATCH {
        override fun index() = 3
        override val key: String
            get() = "COMPANY_STATE_TYPE_WATCH"

        override fun toString(): String {
            return "관리대상"
        }
    },
    COMPANY_STATE_TYPE_SUSPENSION {
        override fun index() = 4
        override val key: String
            get() = "COMPANY_STATE_TYPE_SUSPENSION"

        override fun toString(): String {
            return "거래중지"
        }
    };

    companion object : EnumFinder<Int, CompanyStateType>(CompanyStateType.values().associateBy { it.index() })
}