package com.wellnetworks.wellcore.domain.enums

interface ICompanyType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class CompanyType : ICompanyType{
    COMPANY_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "COMPANY_TYPE_UNKNOWN"

        override fun toString(): String {
            return "미설정"
        }
    },
    COMPANY_TYPE_DEALER {
        override fun index() = 1
        override val key: String
            get() = "COMPANY_TYPE_DEALER"

        override fun toString(): String {
            return "딜러"
        }
    },
    COMPANY_TYPE_OPENING {
        override fun index() = 2
        override val key: String
            get() = "COMPANY_TYPE_OPENING"

        override fun toString(): String {
            return "개통점"
        }
    },
    COMPANY_TYPE_TOP_UP {
        override fun index() = 3
        override val key: String
            get() = "COMPANY_TYPE_TOP_UP"

        override fun toString(): String {
            return "충전점"
        }
    },
    COMPANY_TYPE_ALLIANCE {
        override fun index() = 4
        override val key: String
            get() = "COMPANY_TYPE_ALLIANCE"

        override fun toString(): String {
            return "기타제휴사"
        }
    },
    COMPANY_TYPE_ORGANIZATION {
        override fun index() = 5
        override val key: String
            get() = "COMPANY_TYPE_ORGANIZATION"

        override fun toString(): String {
            return "기관"
        }
    },
    COMPANY_TYPE_SALES_TEAM {
        override fun index() = 6
        override val key: String
            get() = "COMPANY_TYPE_SALES_TEAM"

        override fun toString(): String {
            return "영업팀"
        }
    },
    COMPANY_TYPE_FREELANCER {
        override fun index() = 7
        override val key: String
            get() = "COMPANY_TYPE_FREELANCER"

        override fun toString(): String {
            return "프리랜서"
        }
    },
    COMPANY_TYPE_API {
        override fun index() = 8
        override val key: String
            get() = "COMPANY_TYPE_API"

        override fun toString(): String {
            return "API"
        }
    };

    companion object : EnumFinder<Int, CompanyType>(CompanyType.values().associateBy { it.index() })
}