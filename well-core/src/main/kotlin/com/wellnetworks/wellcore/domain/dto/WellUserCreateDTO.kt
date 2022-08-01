package com.wellnetworks.wellcore.domain.dto

import com.wellnetworks.wellcore.domain.RuleTypes
import java.time.ZonedDateTime
import java.util.*

data class WellUserCreateDTO (
    val userid: String,
    val rule: RuleTypes = RuleTypes.NONE,
    val permission: List<String>,
    val password_hash: String,
    val modify_datetime: ZonedDateTime,
    val register_datetime: ZonedDateTime,
)