package com.wellnetworks.wellcore.domain.enums

interface ICurrentEmploymentType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class CurrentEmploymentType : ICurrentEmploymentType{
    CURRENT_EMPLOYMENT_TYPE_UNKNOWN {
        override fun index() = -1
        override val key: String
        get() = "CURRENT_EMPLOYMENT_TYPE_UNKNOWN"

        override fun toString(): String {
            return "미설정"
        }
    },
    CURRENT_EMPLOYMENT_TYPE_WELL_NETWORKS {
        override fun index() = 1
        override val key: String
        get() = "CURRENT_EMPLOYMENT_TYPE_WELL_NETWORKS"

        override fun toString(): String {
            return "웰네트웍스"
        }
    },
    CURRENT_EMPLOYMENT_TYPE_PHONE_ISSUE {
        override fun index() = 2
        override val key: String
        get() = "CURRENT_EMPLOYMENT_TYPE_PHONE_ISSUE"

        override fun toString(): String {
            return "폰이슈"
        }
    };

    companion object : EnumFinder<Int, CurrentEmploymentType>(CurrentEmploymentType.values().associateBy { it.index() })
}