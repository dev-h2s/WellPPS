package com.wellnetworks.wellcore.domain.enums

interface IJobType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class JobType : IJobType {
    JOB_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
            get() = "JOB_STATE_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    JOB_TYPE_FULL_TIME_WORKER {
        override fun index() = 1
        override val key: String
            get() = "JOB_TYPE_FULL_TIME_WORKER"

        override fun toString(): String {
            return "정규직"
        }
    },
    JOB_TYPE_TEMPORARY_WORKER {
        override fun index() = 2
        override val key: String
            get() = "JOB_TYPE_TEMPORARY_WORKER"

        override fun toString(): String {
            return "비정규직"
        }
    },
    JOB_TYPE_CONTRACT_WORKER {
        override fun index() = 3
        override val key: String
            get() = "JOB_TYPE_CONTRACT_WORKER"

        override fun toString(): String {
            return "계약직"
        }
    };

    companion object : EnumFinder<Int, JobType>(JobType.values().associateBy { it.index() })
}