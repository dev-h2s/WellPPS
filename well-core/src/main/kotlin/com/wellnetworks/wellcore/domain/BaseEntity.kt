package com.wellnetworks.wellcore.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.ZonedDateTime

@MappedSuperclass
// MappedSuperclass : 여러 엔티티 클래스에서 중복되는 코드를 줄이고 코드 재사용성을 높이기 위해 사용
open class BaseEntity {
    @Column(name = "mod_dt", nullable = true)
    open var modifyDatetime: ZonedDateTime? = ZonedDateTime.now()

    @Column(name = "reg_dt", nullable = true)
    open var registerDatetime: ZonedDateTime? = ZonedDateTime.now()
}