package com.wellnetworks.wellcore.domain.enums

interface IOpeningType {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class OpeningType : IOpeningType{
    OPENING_TYPE_UNKNOWN {
        override fun index() = 0

        override val key : String
            get() = "OPENING_TYPE_UNKNOWN"

        override fun toString(): String {
            return "알수없음"
        }
    },
    OPENING_TYPE_NEW_REGISTRATION {
        override fun index() = 1

        override val key : String
            get() = "OPENING_TYPE_NEW_REGISTRATION"

        override fun toString(): String {
            return "신규가입"
        }
    },
    OPENING_TYPE_NUMBER_TRANSFER {
        override fun index() = 2

        override val key : String
            get() = "OPENING_TYPE_NUMBER_TRANSFER"

        override fun toString(): String {
            return "번호이동"
        }
    },
    OPENING_TYPE_CHANGE_USIM {
        override fun index() = 3

        override val key : String
            get() = "OPENING_TYPE_CHANGE_USIM"

        override fun toString(): String {
            return "유심변경"
        }
    },
    OPENING_TYPE_CHANGE_RESIDENCE_CARD {
        override fun index() = 4

        override val key : String
            get() = "OPENING_TYPE_CHANGE_RESIDENCE_CARD"

        override fun toString(): String {
            return "여권>등록증"
        }
    },
    OPENING_TYPE_TERMINATION_SAMEDAY {
        override fun index() = 5

        override val key : String
            get() = "OPENING_TYPE_TERMINATION_SAMEDAY"

        override fun toString(): String {
            return "당일해지"
        }
    },
    OPENING_TYPE_TERMINATION_SAMEMONTH {
        override fun index() = 6

        override val key : String
            get() = "OPENING_TYPE_TERMINATION_SAMEMONTH"

        override fun toString(): String {
            return "당월해지"
        }
    },
    OPENING_TYPE_CHANGE_NUMBER {
        override fun index() = 7

        override val key : String
            get() = "OPENING_TYPE_CHANGE_NUMBER"

        override fun toString(): String {
            return "번호변경"
        }
    },
    OPENING_TYPE_CHANGE_DEVICE {
        override fun index() = 8

        override val key : String
            get() = "OPENING_TYPE_CHANGE_DEVICE"

        override fun toString(): String {
            return "기기변경"
        }
    },
    OPENING_TYPE_CHANGE_NAME {
        override fun index() = 9

        override val key : String
            get() = "OPENING_TYPE_CHANGE_NAME"

        override fun toString(): String {
            return "명의변경"
        }
    },
    OPENING_TYPE_TERMINATION {
        override fun index() = 10

        override val key : String
            get() = "OPENING_TYPE_TERMINATION"

        override fun toString(): String {
            return "해지"
        }
    },
    OPENING_TYPE_SUSPENSION {
        override fun index() = 11

        override val key : String
            get() = "OPENING_TYPE_SUSPENSION"

        override fun toString(): String {
            return "일시정지"
        }
    },
    OPENING_TYPE_CHANGE_PRODUCT {
        override fun index() = 12

        override val key : String
            get() = "OPENING_TYPE_CHANGE_PRODUCT"

        override fun toString(): String {
            return "요금제변경"
        }
    },
    OPENING_TYPE_IMMEDIATE_TERMINATION {
        override fun index() = 13

        override val key : String
            get() = "OPENING_TYPE_IMMEDIATE_TERMINATION"

        override fun toString(): String {
            return "직권해지"
        }
    },
    OPENING_TYPE_TERMINATION_RECOVERY {
        override fun index() = 14

        override val key : String
            get() = "OPENING_TYPE_TERMINATION_RECOVERY"

        override fun toString(): String {
            return "해지복구"
        }
    },
    OPENING_TYPE_TERM_EXTENSION {
        override fun index() = 15

        override val key : String
            get() = "OPENING_TYPE_TERM_EXTENSION"

        override fun toString(): String {
            return "기간연장"
        }
    },
    OPENING_TYPE_DIRECT_DEBIT {
        override fun index() = 16

        override val key : String
            get() = "OPENING_TYPE_DIRECT_DEBIT"

        override fun toString(): String {
            return "자동이체"
        }
    },
    OPENING_TYPE_REQUEST_DELETE {
        override fun index() = 17

        override val key : String
            get() = "OPENING_TYPE_REQUEST_DELETE"

        override fun toString(): String {
            return "삭제요청"
        }
    };

    companion object : EnumFinder<Int, OpeningType>(OpeningType.values().associateBy { it.index() })
}