package com.wellnetworks.wellcore.domain.enums

interface IDepartmentType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class DepartmentType : IDepartmentType {
    DEPARTMENT_TYPE_UNKNOWN {
        override fun index() = -1
        override val key: String
            get() = "DEPARTMENT_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    DEPARTMENT_TYPE_GENERAL_AFFAIRS_TEAM {
        override fun index() = 1
        override val key: String
            get() = "DEPARTMENT_TYPE_GENERAL_AFFAIRS_TEAM"

        override fun toString(): String {
            return "총괄"
        }
    },
    DEPARTMENT_TYPE_OPENING_TEAM {
        override fun index() = 2
        override val key: String
            get() = "DEPARTMENT_TYPE_OPENING_TEAM"

        override fun toString(): String {
            return "개통실"
        }
    },
    DEPARTMENT_TYPE_SALES_TEAM {
        override fun index() = 3
        override val key: String
            get() = "DEPARTMENT_TYPE_SALES_TEAM"

        override fun toString(): String {
            return "영업팀"
        }
    },
    DEPARTMENT_TYPE_IT_TEAM {
        override fun index() = 4
        override val key: String
            get() = "DEPARTMENT_TYPE_IT_TEAM"

        override fun toString(): String {
            return "IT"
        }
    },
    DEPARTMENT_TYPE_MARKETING_TEAM {
        override fun index() = 5
        override val key: String
            get() = "DEPARTMENT_TYPE_MARKETING_TEAM"

        override fun toString(): String {
            return "마케팅"
        }
    },
    DEPARTMENT_TYPE_STORE_MANAGEMENT_TEAM {
        override fun index() = 6
        override val key: String
            get() = "DEPARTMENT_TYPE_STORE_MANAGEMENT_TEAM"

        override fun toString(): String {
            return "매장관리"
        }
    },
    DEPARTMENT_TYPE_MANAGEMENT_PLANNING_TEAM {
        override fun index() = 7
        override val key: String
            get() = "DEPARTMENT_TYPE_MANAGEMENT_PLANNING_TEAM"

        override fun toString(): String {
            return "경영"
        }
    },
    DEPARTMENT_TYPE_RESEARCH_INSTITUTE {
        override fun index() = 8
        override val key: String
            get() = "DEPARTMENT_TYPE_RESEARCH_INSTITUTE"

        override fun toString(): String {
            return "연구소"
        }
    };

    companion object : EnumFinder<Int, DepartmentType>(DepartmentType.values().associateBy { it.index() })
}