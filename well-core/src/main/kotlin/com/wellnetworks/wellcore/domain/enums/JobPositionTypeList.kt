package com.wellnetworks.wellcore.domain.enums

interface IJobPositionType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class JobPositionType : IJobPositionType {
    JOB_POSITION_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "JOB_POSITION_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    JOB_POSITION_TYPE_CEO {
        override fun index() = 1
        override val key: String
            get() = "JOB_POSITION_TYPE_CEO"

        override fun toString(): String {
            return "대표"
        }
    },
    JOB_POSITION_TYPE_DIRECTOR {
        override fun index() = 2
        override val key: String
            get() = "JOB_POSITION_TYPE_DIRECTOR"

        override fun toString(): String {
            return "이사"
        }
    },
    JOB_POSITION_TYPE_GENERAL_MANAGER {
        override fun index() = 3
        override val key: String
            get() = "JOB_POSITION_TYPE_GENERAL_MANAGER"

        override fun toString(): String {
            return "부장"
        }
    },
    JOB_POSITION_TYPE_TEAM_MANAGER {
        override fun index() = 4
        override val key: String
            get() = "JOB_POSITION_TYPE_TEAM_MANAGER"

        override fun toString(): String {
            return "팀장"
        }
    },
    JOB_POSITION_TYPE_DEPUTY_GENERAL_MANAGER {
        override fun index() = 5
        override val key: String
            get() = "JOB_POSITION_TYPE_DEPUTY_GENERAL_MANAGER"

        override fun toString(): String {
            return "차장"
        }
    },
    JOB_POSITION_TYPE_CHIEF {
        override fun index() = 6
        override val key: String
            get() = "JOB_POSITION_TYPE_CHIEF"

        override fun toString(): String {
            return "실장"
        }
    },
    JOB_POSITION_TYPE_MANAGER {
        override fun index() = 7
        override val key: String
            get() = "JOB_POSITION_TYPE_MANAGER"

        override fun toString(): String {
            return "과장"
        }
    },
    JOB_POSITION_TYPE_ASSISTANT_MANAGER {
        override fun index() = 8
        override val key: String
            get() = "JOB_POSITION_TYPE_ASSISTANT_MANAGER"

        override fun toString(): String {
            return "대리"
        }
    },
    JOB_POSITION_TYPE_SENIOR_STAFF {
        override fun index() = 9
        override val key: String
            get() = "JOB_POSITION_TYPE_SENIOR_STAFF"

        override fun toString(): String {
            return "주임"
        }
    },
    JOB_POSITION_TYPE_STAFF {
        override fun index() = 10
        override val key: String
            get() = "JOB_POSITION_TYPE_STAFF"

        override fun toString(): String {
            return "사원"
        }
    },
    JOB_POSITION_TYPE_PART_TIMER {
        override fun index() = 11
        override val key: String
            get() = "JOB_POSITION_TYPE_PART_TIMER"

        override fun toString(): String {
            return "알바"
        }
    },
    JOB_POSITION_TYPE_INTERN {
        override fun index() = 12
        override val key: String
            get() = "JOB_POSITION_TYPE_INTERN"

        override fun toString(): String {
            return "수습"
        }
    };

    companion object : EnumFinder<Int, JobPositionType>(JobPositionType.values().associateBy { it.index() })
}