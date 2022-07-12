package com.wellnetworks.wellcore.domain.dto

import com.wellnetworks.wellcore.domain.RuleTypes
import java.time.ZonedDateTime
import java.util.*

data class WellUserDTO (
    val idx: UUID? = UUID.randomUUID(),
    val userid: String,
    val rule: RuleTypes = RuleTypes.None,
    val passwordhash: String,
    val password_temporary: String,
    val password_expire: ZonedDateTime,
    val password_ct: Byte = 0,
    val password_certification: Boolean,
    val create_temporary_password_datetime: ZonedDateTime,
    val modify_datetime: ZonedDateTime,
    val register_datetime: ZonedDateTime,
)