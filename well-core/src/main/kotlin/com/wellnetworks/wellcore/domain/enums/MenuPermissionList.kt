package com.wellnetworks.wellcore.domain.enums

import kotlin.reflect.full.isSubclassOf

sealed class MenuPermissionAction {
    object VIEWMENU: MenuPermissionAction() { const val name = "VIEWMENU" } 
    object CREATE: MenuPermissionAction() { const val name = "CREATE" } 
    object READ: MenuPermissionAction() { const val name = "READ" } 
    object UPDATE: MenuPermissionAction() { const val name = "UPDATE" } 
    object DELETE: MenuPermissionAction() { const val name = "DELETE" } 
    object SEARCH: MenuPermissionAction() { const val name = "SEARCH" } 
    object EXCEL: MenuPermissionAction() { const val name = "EXCEL" }

    companion object {
        inline fun values(): List<MenuPermissionAction> =
            MenuPermissionAction::class.sealedSubclasses.mapNotNull { it.objectInstance }
    }
}

sealed class MenuPermission {
    //개통관리
    object OPENING: MenuPermission() { const val myname = "MENU_OPENING" } //개통내역
    object ILLEGAL: MenuPermission() { const val name = "MENU_ILLEGAL" } //부정가입현황
    object OPENING_APP: MenuPermission() { const val name = "MENU_OPENING_APP" } //개통접수내역(APP)
    object DAY_STAT: MenuPermission() { const val name = "MENU_DAY_STAT" } //일별실적내역
    object MONTH_STAT: MenuPermission() { const val name = "MENU_MONTH_STAT" } //월별실적내역

    //충전관리
    object TOPUP: MenuPermission() { const val name = "MENU_TOPUP" } //충전내역
    object DEPOSIT: MenuPermission() { const val name = "MENU_DEPOSIT" } //예치금내역
    object ACCOUNT: MenuPermission() { const val name = "MENU_ACCOUNT" } //가상계좌내역

    //인사관리
    object PARTNER: MenuPermission() { const val name = "MENU_PARTNER" } //거래처리스트
    object MEMBER: MenuPermission() { const val name = "MENU_MEMBER" } //사원관리
    object SIGNUP: MenuPermission() { const val name = "MENU_SIGNUP" } //회원가입관리
    object VISITOR: MenuPermission() { const val name = "MENU_VISITOR" } //개통점신청
    object API: MenuPermission() { const val name = "MENU_API" } //API 리스트
    object AUTH: MenuPermission() { const val name = "MENU_AUTH" } //권한관리
    object CONFIRM: MenuPermission() { const val name = "MENU_CONFIRM" } //품의/결제
    object SALARY: MenuPermission() { const val name = "MENU_SALARY" } //급여명세서

    //재고관리
    object INVENTORY: MenuPermission() { const val name = "MENU_INVENTORY" } //전체재고관리
    object INVENTORY_DETAIL: MenuPermission() { const val name = "MENU_INVENTORY_DETAIL" } //전체재고상세
    object PRODUCT_STATUS: MenuPermission() { const val name = "MENU_PRODUCT_STATUS" } //제품고유값현황
    object RECEIPT: MenuPermission() { const val name = "MENU_RECEIPT" } //인수증관리

    //주문관리
    object ORDER: MenuPermission() { const val name = "MENU_ORDER" } //제품주문
    object ORDER_STATE: MenuPermission() { const val name = "MENU_ORDER_STATE" } //주문내역서

    //정산관리
    object CAL_HISTORY: MenuPermission() { const val name = "MENU_CAL_HISTORY" } //정산내역(건별)
    object CAL_PARTNER: MenuPermission() { const val name = "MENU_CAL_PARTNER" } //정산내역(거래처별)
    object CAL_DAY: MenuPermission() { const val name = "MENU_CAL_DAY" } //일일정산
    object CASH: MenuPermission() { const val name = "MENU_CASH" } //법인현금
    object ACCOUNTING: MenuPermission() { const val name = "MENU_ACCOUNTING" } //전체회계
    object ACCOUNTING_CORP: MenuPermission() { const val name = "MENU_ACCOUNTING_CORP" } //법인회계

    //부가서비스
    object SMS: MenuPermission() { const val name = "MENU_SMS" } //문자보내기
    object SMS_LIST: MenuPermission() { const val name = "MENU_SMS_LIST" } //문자발송내역
    object SCHEDULE: MenuPermission() { const val name = "MENU_SCHEDULE" } //일정관리

    //게시판
    object NOTICE: MenuPermission() { const val name = "MENU_NOTICE" } //공지사항
    object WORK_REALTIME: MenuPermission() { const val name = "MENU_WORK_REALTIME" } //실시간업무
    object DAYWORK_CHECK: MenuPermission() { const val name = "MENU_DAYWORK_CHECK" } //일일업무체크
    object WORK_REPORT: MenuPermission() { const val name = "MENU_WORK_REPORT" } //업무보고
    object PRIVATE: MenuPermission() { const val name = "MENU_PRIVATE" } //프라이빗

    //자료실
    object APPLICATION: MenuPermission() { const val name = "MENU_APPLICATION" } //신청서다운로드
    object MEETING_REPORT: MenuPermission() { const val name = "MENU_MEETING_REPORT" } //회의록
    object WORKING_MENUAL: MenuPermission() { const val name = "MENU_WORKING_MENUAL" } //업무메뉴얼
    object NEW_PROJECT: MenuPermission() { const val name = "MENU_NEW_PROJECT" } //신규프로젝트

    //관리
    object CODE_MANAGEMENT: MenuPermission() { const val name = "MENU_CODE_MANAGEMENT" } //코드관리
    object PRODUCT_MANAGEMENT: MenuPermission() { const val name = "MENU_PRODUCT_MANAGEMENT" } //통신사요금제관리
    object OPENING_POLICY: MenuPermission() { const val name = "MENU_OPENING_POLICY" } //개통정책
    object TOPUP_POLICY: MenuPermission() { const val name = "MENU_TOPUP_POLICY" } //충전정책
    object SPECIAL_POLICY: MenuPermission() { const val name = "MENU_SPECIAL_POLICY" } //특수정책
    object LOG: MenuPermission() { const val name = "MENU_LOG" } //로그내역
    object PRODUCT_CODE_MANAGEMENT: MenuPermission() { const val name = "MENU_PRODUCT_CODE_MANAGEMENT" } //제품코드관리
    object POPUP: MenuPermission() { const val name = "MENU_POPUP" } //팝업관리

    companion object {
        inline fun values(): List<MenuPermission> =
            MenuPermission::class.sealedSubclasses.mapNotNull { it.objectInstance }
    }
}
/*
class MenuPermissionUtil {
    companion object {
        fun BuildPermissionStringAll(): List<String> {
            var permissionStringList: MutableList<String> = mutableListOf()

            for (menuPermission in MenuPermission.values()) {
                for (menuPermissionType in MenuPermissionAction.values()) {
                    permissionStringList.add(BuildPermissionString(menuPermission, menuPermissionType))
                }
            }

            return permissionStringList.toList()
        }

        fun BuildPermissionString(menuPermission: MenuPermission, action: MenuPermissionAction) : String {
            return "${menuPermission.name}_${action.}"
        }

        fun ParsePermissionString(GroupPermissionString: String): Pair<MenuPermissionList, MenuPermissionTypeList>? {
            var splitPermissionString = GroupPermissionString.uppercase().split('_').toMutableList()

            if (splitPermissionString.isNullOrEmpty()) return null

            var menuPermissionType: MenuPermissionTypeList;
            try {
                menuPermissionType = MenuPermissionTypeList.valueOf(splitPermissionString.last())
                splitPermissionString.removeAt(splitPermissionString.lastIndex)
            } catch (e: Exception) {
                return null
            }

            var menuPermission: MenuPermissionList;
            try {
                menuPermission = MenuPermissionList.valueOf(splitPermissionString.joinToString("_"))
            } catch (e: Exception) {
                return null
            }

            return Pair(menuPermission, menuPermissionType)
        }
    }
}*/
