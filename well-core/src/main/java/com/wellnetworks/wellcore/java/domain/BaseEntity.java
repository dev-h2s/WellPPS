package com.wellnetworks.wellcore.java.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; //yyyy-mm-dd hh:mm:ss

    @LastModifiedDate
    private LocalDateTime updatedAt; //yyyy-mm-dd hh:mm:ss

    @Column(name = "writer", updatable = false) //작성자
    @CreatedBy
    private String writer;

    @Column(name = "updater") //수정자
    @LastModifiedBy
    private String updater;
}
