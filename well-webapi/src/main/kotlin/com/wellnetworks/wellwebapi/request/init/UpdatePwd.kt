package com.wellnetworks.wellwebapi.request.init

class UpdatePwd(var idx: String, var password: String, var type: Int){
    //type : 1 비밀번호
    //typ2 : 2 임시비밀번호
}