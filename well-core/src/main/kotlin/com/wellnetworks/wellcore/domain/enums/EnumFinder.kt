package com.wellnetworks.wellcore.domain.enums

abstract class EnumFinder<V, E>(private val valueMap: Map<V, E>) {
    infix fun from(value: V) = valueMap[value]
}