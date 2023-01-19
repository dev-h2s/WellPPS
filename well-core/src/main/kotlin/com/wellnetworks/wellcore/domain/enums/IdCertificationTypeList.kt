package com.wellnetworks.wellcore.domain.enums

interface IIdCertificationType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class IdCertificationType : IIdCertificationType{
    ID_CERTIFICATION_TYPE_UNKNOWN {
        override fun index() = 0
        override val key: String
        get() = "ID_CERTIFICATION_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    ID_CERTIFICATION_TYPE_OPENING {
        override fun index() = 1
        override val key: String
        get() = "ID_CERTIFICATION_TYPE_OPENING"

        override fun toString(): String {
            return "인증"
        }
    },
    ID_CERTIFICATION_TYPE_TOP_UP {
        override fun index() = 2
        override val key: String
        get() = "ID_CERTIFICATION_TYPE_TOP_UP"

        override fun toString(): String {
            return "미인증"
        }
    };

    companion object : EnumFinder<Int, IdCertificationType>(IdCertificationType.values().associateBy { it.index() })
}