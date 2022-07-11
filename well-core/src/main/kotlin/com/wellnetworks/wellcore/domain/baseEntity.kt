package com.wellnetworks.wellcore.domain

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class baseEntity {
    @Column(name = "mod_dt")
    var modify_datetime: ZonedDateTime = ZonedDateTime.now()

    @Column(name = "reg_dt")
    var register_datetime: ZonedDateTime = ZonedDateTime.now()
}