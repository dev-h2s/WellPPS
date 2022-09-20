package com.wellnetworks.wellcore.domain.dto

import java.time.ZonedDateTime
import java.util.*

data class WellMemberInfoDTO(
    val idx: UUID? = UUID.randomUUID(),
    val user_idx: String,
    val table_id: String,
    val belong: List<String>,
    val name: String,
    val email: String,
    val phone: String,
    val well_phone: String,
    val jumin: String,
    val dep: List<String>,
    val pos: List<String>,
    val level: List<String>,
    val addr1: String,
    val addr2: String,
    val bank_name: List<String>,
    val b_account: String,
    val b_holder: String,
    val status: List<String>,
    val type: List<String>,
    val phone_cert: Boolean,
    val email_cert: Boolean,
    val entry_dt: ZonedDateTime,
    val retire_dt: ZonedDateTime,
    val retire_type: List<String>,
    val access: Boolean,
    val memo: String,
    val modify_datetime: ZonedDateTime,
    val register_datetime: ZonedDateTime,
    )