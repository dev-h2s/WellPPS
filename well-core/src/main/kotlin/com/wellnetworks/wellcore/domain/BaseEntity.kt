package com.wellnetworks.wellcore.domain

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity {
    @Column(name = "mod_dt", nullable = true)
    open var modifyDatetime: ZonedDateTime? = ZonedDateTime.now()

    @Column(name = "reg_dt", nullable = true)
    open var registerDatetime: ZonedDateTime? = ZonedDateTime.now()
}