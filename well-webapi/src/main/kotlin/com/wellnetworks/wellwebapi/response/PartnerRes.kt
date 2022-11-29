package com.wellnetworks.wellwebapi.response

import org.springframework.http.HttpStatus

class PartnerRes(status: HttpStatus, message: String): BaseRes(status, message) {

}