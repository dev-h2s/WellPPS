package com.wellnetworks.wellcore.domain.enums

import kotlin.reflect.full.companionObject
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties

annotation class MenuPermissionAction {
    companion object {
        const val VIEWMENU = "VIEWMENU"
        const val CREATE = "CREATE"
        const val READ = "READ"
        const val UPDATE = "UPDATE"
        const val DELETE = "DELETE"
        const val SEARCH = "SEARCH"
        const val EXCEL = "EXCEL"

        fun asMap() : Map<String, String> {
            val props = MenuPermissionAction::class.companionObject!!.memberProperties.associateBy { it.name }
            return props.keys.associateWith { props[it]?.call().toString()!! }
        }
    }
}

annotation class MenuPermission {
    companion object {
        //개통관리
        const val OPENING = "MENU_OPENING" //개통내역
        const val ILLEGAL = "MENU_ILLEGAL" //부정가입현황
        const val OPENING_APP = "MENU_OPENING_APP" //개통접수내역(APP)
        const val DAY_STAT = "MENU_DAY_STAT" //일별실적내역
        const val MONTH_STAT = "MENU_MONTH_STAT" //월별실적내역
    
        //충전관리
        const val TOPUP = "MENU_TOPUP" //충전내역
        const val DEPOSIT = "MENU_DEPOSIT" //예치금내역
        const val ACCOUNT = "MENU_ACCOUNT" //가상계좌내역
    
        //인사관리
        const val PARTNER = "MENU_PARTNER" //거래처리스트
        const val MEMBER = "MENU_MEMBER" //사원관리
        const val SIGNUP = "MENU_SIGNUP" //회원가입관리
        const val VISITOR = "MENU_VISITOR" //개통점신청
        const val API = "MENU_API" //API 리스트
        const val AUTH = "MENU_AUTH" //권한관리
        const val CONFIRM = "MENU_CONFIRM" //품의/결제
        const val SALARY = "MENU_SALARY" //급여명세서
    
        //재고관리
        const val INVENTORY = "MENU_INVENTORY" //전체재고관리
        const val INVENTORY_DETAIL = "MENU_INVENTORY_DETAIL" //전체재고상세
        const val PRODUCT_STATUS = "MENU_PRODUCT_STATUS" //제품고유값현황
        const val RECEIPT = "MENU_RECEIPT" //인수증관리
    
        //주문관리
        const val ORDER = "MENU_ORDER" //제품주문
        const val ORDER_STATE = "MENU_ORDER_STATE" //주문내역서
    
        //정산관리
        const val CAL_HISTORY = "MENU_CAL_HISTORY" //정산내역(건별)
        const val CAL_PARTNER = "MENU_CAL_PARTNER" //정산내역(거래처별)
        const val CAL_DAY = "MENU_CAL_DAY" //일일정산
        const val CASH = "MENU_CASH" //법인현금
        const val ACCOUNTING = "MENU_ACCOUNTING" //전체회계
        const val ACCOUNTING_CORP = "MENU_ACCOUNTING_CORP" //법인회계
    
        //부가서비스
        const val SMS = "MENU_SMS" //문자보내기
        const val SMS_LIST = "MENU_SMS_LIST" //문자발송내역
        const val SCHEDULE = "MENU_SCHEDULE" //일정관리
    
        //게시판
        const val NOTICE = "MENU_NOTICE" //공지사항
        const val WORK_REALTIME = "MENU_WORK_REALTIME" //실시간업무
        const val DAYWORK_CHECK = "MENU_DAYWORK_CHECK" //일일업무체크
        const val WORK_REPORT = "MENU_WORK_REPORT" //업무보고
        const val PRIVATE = "MENU_PRIVATE" //프라이빗
    
        //자료실
        const val APPLICATION = "MENU_APPLICATION" //신청서다운로드
        const val MEETING_REPORT = "MENU_MEETING_REPORT" //회의록
        const val WORKING_MENUAL = "MENU_WORKING_MENUAL" //업무메뉴얼
        const val NEW_PROJECT = "MENU_NEW_PROJECT" //신규프로젝트
    
        //관리
        const val CODE_MANAGEMENT = "MENU_CODE_MANAGEMENT" //코드관리
        const val PRODUCT_MANAGEMENT = "MENU_PRODUCT_MANAGEMENT" //통신사요금제관리
        const val OPENING_POLICY = "MENU_OPENING_POLICY" //개통정책
        const val TOPUP_POLICY = "MENU_TOPUP_POLICY" //충전정책
        const val SPECIAL_POLICY = "MENU_SPECIAL_POLICY" //특수정책
        const val LOG = "MENU_LOG" //로그내역
        const val PRODUCT_CODE_MANAGEMENT = "MENU_PRODUCT_CODE_MANAGEMENT" //제품코드관리
        const val POPUP = "MENU_POPUP" //팝업관리

        fun asMap() : Map<String, String> {
            val props = MenuPermission::class.companionObject!!.memberProperties.associateBy { it.name }
            return props.keys.associateWith { props[it]?.call().toString()!! }
        }
    }
}

class MenuPermissionUtil {
    companion object {
        val menuPermissionMap = MenuPermission.asMap()
        val menuPermissionActionMap = MenuPermissionAction.asMap()

        fun BuildPermissionStringAll(): List<String> {
            var permissionStringList: MutableList<String> = mutableListOf()

            for (menuPermission in menuPermissionMap.values) {
                for (menuPermissionType in menuPermissionActionMap.values) {
                    permissionStringList.add(BuildPermissionString(menuPermission, menuPermissionType))
                }
            }

            return permissionStringList.toList()
        }

        fun ParsePermissionString(groupPermissionString: String): Pair<String, String>? {
            var splitPermissionString = groupPermissionString.uppercase().split('_').toMutableList()

            if (splitPermissionString.isNullOrEmpty()) return null

            val actionSearch = menuPermissionActionMap.filterValues { it == splitPermissionString.last() }.keys.firstOrNull()
                ?: return null
            splitPermissionString.removeAt(splitPermissionString.lastIndex)

            val menuPermissionSearch = menuPermissionMap.filterKeys { it == splitPermissionString.joinToString("_") }.keys.firstOrNull()
                ?: return null

            return Pair(actionSearch, menuPermissionSearch)
        }

        fun BuildPermissionString(menuPermission: String, action: String) : String {
            return "${menuPermission}_${action}"
        }
    }
}

interface PermissionEnumList {
    fun index() : Int
    val key: String
    override fun toString() : String
}

enum class PermissionList : PermissionEnumList {
    MENU_OPENING {
        override fun index() = 0
        override val key: String
            get() = "MENU_OPENING"

        override fun toString(): String {
            return "개통내역"
        }
    },
    MENU_OPENING_APP {
        override fun index() = 1
        override val key: String
            get() = "MENU_OPENING_APP"

        override fun toString(): String {
            return "개통접수내역(APP)"
        }
    },
    MENU_ILLEGAL {
        override fun index() = 2
        override val key: String
            get() = "MENU_ILLEGAL"

        override fun toString(): String {
            return "부정가입현황"
        }
    },
    MENU_DAY_STAT {
        override fun index() = 3
        override val key: String
            get() = "MENU_DAY_STAT"

        override fun toString(): String {
            return "일별실적내역"
        }
    },
    MENU_MONTH_STAT {
        override fun index() = 4
        override val key: String
            get() = "MENU_MONTH_STAT"

        override fun toString(): String {
            return "월별실적내역"
        }
    };

    companion object : EnumFinder<Int, PermissionList>(PermissionList.values().associateBy { it.index() })
}

interface ActionEnumList {
    fun index() : Int
    val key: String
    override fun toString() : String
}
enum class ActionList : ActionEnumList {
    VIEWMENU {
        override fun index() = 0
        override val key: String
            get() = "VIEWMENU"

        override fun toString(): String {
            return "열람"
        }
    },
    CREATE {
        override fun index() = 1
        override val key: String
            get() = "CREATE"

        override fun toString(): String {
            return "입력"
        }
    },
    READ {
        override fun index() = 2
        override val key: String
            get() = "READ"

        override fun toString(): String {
            return "보기"
        }
    },
    UPDATE {
        override fun index() = 3
        override val key: String
            get() = "UPDATE"

        override fun toString(): String {
            return "수정"
        }
    },
    DELETE {
        override fun index() = 4
        override val key: String
            get() = "DELETE"

        override fun toString(): String {
            return "삭제"
        }
    },
    SEARCH {
        override fun index() = 5
        override val key: String
            get() = "SEARCH"

        override fun toString(): String {
            return "검색"
        }
    },
    EXCEL {
        override fun index() = 6
        override val key: String
            get() = "EXCEL"

        override fun toString(): String {
            return "엑셀"
        }
    };

    companion object : EnumFinder<Int, ActionList>(ActionList.values().associateBy { it.index() })
}
