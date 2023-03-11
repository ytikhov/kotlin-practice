package com.ytikhov.jpalocks.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_shared")
@EntityListeners(AuditingEntityListener::class)
open class SharedEntity() : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(name = "shared_value")
    open var sharedValue: Long = 0L

    @Version
    @Column(name = "version", nullable = false)
    open var version: Int = 0

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    open var createdDate: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "last_modified_date")
    open var lastModifiedDate: LocalDateTime? = null

    override fun toString(): String {
        return "SharedEntity(id=$id, sharedValue=$sharedValue, version=$version, createdDate=$createdDate, lastModifiedDate=$lastModifiedDate)"
    }
}