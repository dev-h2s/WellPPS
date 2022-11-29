package com.wellnetworks.wellcore.domain

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity {
    @Column(name = "mod_dt")
    open var ModifyDatetime: ZonedDateTime = ZonedDateTime.now()

    @Column(name = "reg_dt")
    open var RegisterDatetime: ZonedDateTime = ZonedDateTime.now()
}