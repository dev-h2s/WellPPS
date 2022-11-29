package com.wellnetworks.wellcore.domain.enums

interface IContactRejectType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class ContactRejectType : IContactRejectType {
    CONTACT_REJECT_TYPE_UNKNOWN {
        override fun index() = -1
        override val key: String
            get() = "CONTACT_REJECT_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    CONTACT_REJECT_TYPE_CEO_NAME_DISAGREE {
        override fun index() = 1
        override val key: String
            get() = "CONTACT_REJECT_TYPE_CEO_NAME_DISAGREE"

        override fun toString(): String {
            return "대표자명 불일치"
        }
    },
    CONTACT_REJECT_TYPE_COMPANY_NAME_DISAGREE {
        override fun index() = 2
        override val key: String
            get() = "CONTACT_REJECT_TYPE_COMPANY_NAME_DISAGREE"

        override fun toString(): String {
            return "거래처명 불일치"
        }
    },
    CONTACT_REJECT_TYPE_TAX_CODE_DISAGREE {
        override fun index() = 3
        override val key: String
            get() = "CONTACT_REJECT_TYPE_TAX_CODE_DISAGREE"

        override fun toString(): String {
            return "사업자등록변호 불일치"
        }
    },
    CONTACT_REJECT_TYPE_ATTACHED_FILE_NOT_FOUND {
        override fun index() = 4
        override val key: String
            get() = "CONTACT_REJECT_TYPE_ATTACHED_FILE_NOT_FOUND"

        override fun toString(): String {
            return "첨부파일 미첨부"
        }
    },
    CONTACT_REJECT_TYPE_BED_ATTACHED_FILE {
        override fun index() = 5
        override val key: String
            get() = "CONTACT_REJECT_TYPE_BED_ATTACHED_FILE"

        override fun toString(): String {
            return "첨부파일 인식불가"
        }
    },
    CONTACT_REJECT_TYPE_NOT_QUALIFIED {
        override fun index() = 6
        override val key: String
            get() = "CONTACT_REJECT_TYPE_NOT_QUALIFIED"

        override fun toString(): String {
            return "사업자등록 1년 미만"
        }
    };

    companion object : EnumFinder<Int, ContactRejectType>(ContactRejectType.values().associateBy { it.index() })
}