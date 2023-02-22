package com.wellnetworks.wellcore.domain.enums

enum class MenuPermissionTypeList(val MenuPermissionTypeKey: String) {
    MENU_PERMISSION_TYPE_VIEWMENU("VIEWMENU"),
    MENU_PERMISSION_TYPE_CREATE("CREATE"),
    MENU_PERMISSION_TYPE_READ("READ"),
    MENU_PERMISSION_TYPE_UPDATE("UPDATE"),
    MENU_PERMISSION_TYPE_DELETE("DELETE"),
    MENU_PERMISSION_TYPE_SEARCH("SEARCH"),
    MENU_PERMISSION_TYPE_EXCEL("EXCEL"),
}

enum class MenuPermissionList(val MenuPermitssionKey: String) {
    //개통관리
    MENU_PERMISSION_OPENING("MENU_OPENING"),//개통내역
    MENU_PERMISSION_ILLEGAL("MENU_ILLEGAL"),//부정가입현황
    MENU_PERMISSION_OPENING_APP("MENU_OPENING_APP"),//개통접수내역(APP)
    MENU_PERMISSION_DAY_STAT("MENU_DAY_STAT"),//일별실적내역
    MENU_PERMISSION_MONTH_STAT("MENU_MONTH_STAT"),//월별실적내역

    //충전관리
    MENU_PERMISSION_TOPUP("MENU_TOPUP"),//충전내역
    MENU_PERMISSION_DEPOSIT("MENU_DEPOSIT"),//예치금내역
    MENU_PERMISSION_ACCOUNT("MENU_ACCOUNT"),//가상계좌내역

    //인사관리
    MENU_PERMISSION_PARTNER("MENU_PARTNER"),//거래처리스트
    MENU_PERMISSION_MEMBER("MENU_MEMBER"),//사원관리
    MENU_PERMISSION_SIGNUP("MENU_SIGNUP"),//회원가입관리
    MENU_PERMISSION_VISITOR("MENU_VISITOR"),//개통점신청
    MENU_PERMISSION_API("MENU_API"),//API 리스트
    MENU_PERMISSION_AUTH("MENU_AUTH"),//권한관리
    MENU_PERMISSION_CONFIRM("MENU_CONFIRM"),//품의/결제
    MENU_PERMISSION_SALARY("MENU_SALARY"),//급여명세서

    //재고관리
    MENU_PERMISSION_INVENTORY("MENU_INVENTORY"),//전체재고관리
    MENU_PERMISSION_INVENTORY_DETAIL("MENU_INVENTORY_DETAIL"),//전체재고상세
    MENU_PERMISSION_PRODUCT_STATUS("MENU_PRODUCT_STATUS"),//제품고유값현황
    MENU_PERMISSION_RECEIPT("MENU_RECEIPT"),//인수증관리

    //주문관리
    MENU_PERMISSION_ORDER("MENU_ORDER"),//제품주문
    MENU_PERMISSION_ORDER_STATE("MENU_ORDER_STATE"),//주문내역서

    //정산관리
    MENU_PERMISSION_CAL_HISTORY("MENU_CAL_HISTORY"),//정산내역(건별)
    MENU_PERMISSION_CAL_PARTNER("MENU_CAL_PARTNER"),//정산내역(거래처별)
    MENU_PERMISSION_CAL_DAY("MENU_CAL_DAY"),//일일정산
    MENU_PERMISSION_CASH("MENU_CASH"),//법인현금
    MENU_PERMISSION_ACCOUNTING("MENU_ACCOUNTING"),//전체회계
    MENU_PERMISSION_ACCOUNTING_CORP("MENU_ACCOUNTING_CORP"),//법인회계

    //부가서비스
    MENU_PERMISSION_SMS("MENU_SMS"),//문자보내기
    MENU_PERMISSION_SMS_LIST("MENU_SMS_LIST"),//문자발송내역
    MENU_PERMISSION_SCHEDULE("MENU_SCHEDULE"),//일정관리

    //게시판
    MENU_PERMISSION_NOTICE("MENU_NOTICE"),//공지사항
    MENU_PERMISSION_WORK_REALTIME("MENU_WORK_REALTIME"),//실시간업무
    MENU_PERMISSION_DAYWORK_CHECK("MENU_DAYWORK_CHECK"),//일일업무체크
    MENU_PERMISSION_WORK_REPORT("MENU_WORK_REPORT"),//업무보고
    MENU_PERMISSION_PRIVATE("MENU_PRIVATE"),//프라이빗

    //자료실
    MENU_PERMISSION_APPLICATION("MENU_APPLICATION"),//신청서다운로드
    MENU_PERMISSION_MEETING_REPORT("MENU_MEETING_REPORT"),//회의록
    MENU_PERMISSION_WORKING_MENUAL("MENU_WORKING_MENUAL"),//업무메뉴얼
    MENU_PERMISSION_NEW_PROJECT("MENU_NEW_PROJECT"),//신규프로젝트

    //관리
    MENU_PERMISSION_CODE_MANAGEMENT("MENU_CODE_MANAGEMENT"),//코드관리
    MENU_PERMISSION_PRODUCT_MANAGEMENT("MENU_PRODUCT_MANAGEMENT"),//통신사요금제관리
    MENU_PERMISSION_OPENING_POLICY("MENU_OPENING_POLICY"),//개통정책
    MENU_PERMISSION_TOPUP_POLICY("MENU_TOPUP_POLICY"),//충전정책
    MENU_PERMISSION_SPECIAL_POLICY("MENU_SPECIAL_POLICY"),//특수정책
    MENU_PERMISSION_LOG("MENU_LOG"),//로그내역
    MENU_PERMISSION_PRODUCT_CODE_MANAGEMENT("MENU_PRODUCT_CODE_MANAGEMENT"),//제품코드관리
    MENU_PERMISSION_POPUP("MENU_POPUP")//팝업관리


}

class MenuPermissionUtil {
    companion object {
        fun BuildPermissionStringAll(): List<String> {
            var permissionStringList: MutableList<String> = mutableListOf()

            for (menuPermission in MenuPermissionList.values()) {
                for (menuPermissionType in MenuPermissionTypeList.values()) {
                    permissionStringList.add(BuildPermissionString(menuPermission, menuPermissionType))
                }
            }

            return permissionStringList.toList()
        }

        fun BuildPermissionString(menuPermission: MenuPermissionList, action: MenuPermissionTypeList) : String {
            return "${menuPermission.MenuPermitssionKey}_${action.MenuPermissionTypeKey}"
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
}