package com.wellnetworks.wellwebapi.response

import com.wellnetworks.wellcore.domain.enums.CompanyStateType
import org.springframework.http.HttpStatus

class PartnerCntRes(var regCount: Long, var tmpCount: Long, var watchCount: Long, var susCount: Long) {

}