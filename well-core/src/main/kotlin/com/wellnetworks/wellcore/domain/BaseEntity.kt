package com.wellnetworks.wellcore.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.ZonedDateTime

@MappedSuperclass
open class BaseEntity {
    @Column(name = "mod_dt", nullable = true)
    open var modifyDatetime: ZonedDateTime? = ZonedDateTime.now()

    @Column(name = "reg_dt", nullable = true)
    open var registerDatetime: ZonedDateTime? = ZonedDateTime.now()
}