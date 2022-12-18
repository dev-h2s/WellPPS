package com.wellnetworks.wellcore.domain.dto

import java.time.ZonedDateTime

abstract class BaseDTO (
    open val Modify_Datetime: ZonedDateTime?,
    open val Register_Datetime: ZonedDateTime?,
)