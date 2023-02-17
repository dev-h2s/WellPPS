package com.wellnetworks.wellcore.domain.enums

enum class MenuPermissionTypeList(val GroupPermissionTypeKey: String) {
    GROUP_PERMISSION_TYPE_VIEWMENU("VIEWMENU"),
    GROUP_PERMISSION_TYPE_CREATE("CREATE"),
    GROUP_PERMISSION_TYPE_READ("READ"),
    GROUP_PERMISSION_TYPE_UPDATE("UPDATE"),
    GROUP_PERMISSION_TYPE_DELETE("DELETE"),
    GROUP_PERMISSION_TYPE_SEARCH("SEARCH"),
    GROUP_PERMISSION_TYPE_EXCEL("EXCEL"),
}

enum class MenuPermissionList(val GroupPermitssionKey: String) {
    GROUP_PERMISSION_OPENING("MENU_OPENING")

}