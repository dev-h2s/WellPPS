package com.wellnetworks.wellcore.domain

import java.time.ZonedDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class baseEntity {
    abstract val mod_dt: ZonedDateTime
    abstract val reg_dt: ZonedDateTime
}