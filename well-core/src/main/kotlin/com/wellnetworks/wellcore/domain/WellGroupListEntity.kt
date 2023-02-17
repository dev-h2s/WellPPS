package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import java.util.*
import java.time.ZonedDateTime
import javax.persistence.*
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.*
import org.hibernate.Hibernate
import org.hibernate.annotations.ColumnDefault
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_group_list_tb", indexes =
  [Index(name = "IX_uid", columnList = "uid ASC", unique = true)]
)
data class WellGroupListEntity(
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    @Column(name = "label", length = 32, nullable = false)
    var Label: String,

    @Column(name = "permissions", nullable = true)
    @Convert(converter = ListToStringConverter::class)
    var permissionsKeysStringList: List<String>,

    @Column(name = "description", length = 255, nullable = true)
    var description: String?,
): BaseEntity()


